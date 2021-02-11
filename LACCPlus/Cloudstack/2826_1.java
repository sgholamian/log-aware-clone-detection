//,temp,LibvirtCreatePrivateTemplateFromVolumeCommandWrapper.java,64,177,temp,KVMStorageProcessor.java,525,650
//,3
public class xxx {
    @Override
    public Answer execute(final CreatePrivateTemplateFromVolumeCommand command, final LibvirtComputingResource libvirtComputingResource) {
        final String secondaryStorageURL = command.getSecondaryStorageUrl();

        KVMStoragePool secondaryStorage = null;
        KVMStoragePool primary = null;
        final KVMStoragePoolManager storagePoolMgr = libvirtComputingResource.getStoragePoolMgr();
        try {
            final String templateFolder = command.getAccountId() + File.separator + command.getTemplateId() + File.separator;
            final String templateInstallFolder = "/template/tmpl/" + templateFolder;

            secondaryStorage = storagePoolMgr.getStoragePoolByURI(secondaryStorageURL);

            try {
                primary = storagePoolMgr.getStoragePool(command.getPool().getType(), command.getPrimaryStoragePoolNameLabel());
            } catch (final CloudRuntimeException e) {
                if (e.getMessage().contains("not found")) {
                    primary =
                            storagePoolMgr.createStoragePool(command.getPool().getUuid(), command.getPool().getHost(), command.getPool().getPort(), command.getPool().getPath(),
                                    command.getPool().getUserInfo(), command.getPool().getType());
                } else {
                    return new CreatePrivateTemplateAnswer(command, false, e.getMessage());
                }
            }

            final KVMPhysicalDisk disk = primary.getPhysicalDisk(command.getVolumePath());
            final String tmpltPath = secondaryStorage.getLocalPath() + File.separator + templateInstallFolder;
            final StorageLayer storage = libvirtComputingResource.getStorage();
            storage.mkdirs(tmpltPath);

            if (primary.getType() != StoragePoolType.RBD) {
                final String createTmplPath = libvirtComputingResource.createTmplPath();
                final int cmdsTimeout = libvirtComputingResource.getCmdsTimeout();

                final Script scriptCommand = new Script(createTmplPath, cmdsTimeout, s_logger);
                scriptCommand.add("-f", disk.getPath());
                scriptCommand.add("-t", tmpltPath);
                scriptCommand.add("-n", command.getUniqueName() + ".qcow2");

                final String result = scriptCommand.execute();

                if (result != null) {
                    s_logger.debug("failed to create template: " + result);
                    return new CreatePrivateTemplateAnswer(command, false, result);
                }
            } else {
                s_logger.debug("Converting RBD disk " + disk.getPath() + " into template " + command.getUniqueName());

                final QemuImgFile srcFile =
                        new QemuImgFile(KVMPhysicalDisk.RBDStringBuilder(primary.getSourceHost(), primary.getSourcePort(), primary.getAuthUserName(),
                                primary.getAuthSecret(), disk.getPath()));
                srcFile.setFormat(PhysicalDiskFormat.RAW);

                final QemuImgFile destFile = new QemuImgFile(tmpltPath + "/" + command.getUniqueName() + ".qcow2");
                destFile.setFormat(PhysicalDiskFormat.QCOW2);

                final QemuImg q = new QemuImg(0);
                try {
                    q.convert(srcFile, destFile);
                } catch (final QemuImgException e) {
                    s_logger.error("Failed to create new template while converting " + srcFile.getFileName() + " to " + destFile.getFileName() + " the error was: " +
                            e.getMessage());
                }

                final File templateProp = new File(tmpltPath + "/template.properties");
                if (!templateProp.exists()) {
                    templateProp.createNewFile();
                }

                String templateContent = "filename=" + command.getUniqueName() + ".qcow2" + System.getProperty("line.separator");

                final DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy");
                final Date date = new Date();
                templateContent += "snapshot.name=" + dateFormat.format(date) + System.getProperty("line.separator");

                try(FileOutputStream templFo = new FileOutputStream(templateProp);) {
                    templFo.write(templateContent.getBytes("UTF-8"));
                    templFo.flush();
                }catch(final IOException ex)
                {
                    s_logger.error("CreatePrivateTemplateAnswer:Exception:"+ex.getMessage());
                }

            }

            final Map<String, Object> params = new HashMap<String, Object>();
            params.put(StorageLayer.InstanceConfigKey, storage);
            final Processor qcow2Processor = new QCOW2Processor();

            qcow2Processor.configure("QCOW2 Processor", params);

            final FormatInfo info = qcow2Processor.process(tmpltPath, null, command.getUniqueName());

            final TemplateLocation loc = new TemplateLocation(storage, tmpltPath);
            loc.create(1, true, command.getUniqueName());
            loc.addFormat(info);
            loc.save();

            return new CreatePrivateTemplateAnswer(command, true, null, templateInstallFolder + command.getUniqueName() + ".qcow2", info.virtualSize, info.size,
                    command.getUniqueName(), ImageFormat.QCOW2);
        } catch (final InternalErrorException e) {
            return new CreatePrivateTemplateAnswer(command, false, e.toString());
        } catch (final IOException e) {
            return new CreatePrivateTemplateAnswer(command, false, e.toString());
        } catch (final ConfigurationException e) {
            return new CreatePrivateTemplateAnswer(command, false, e.toString());
        } catch (final CloudRuntimeException e) {
            return new CreatePrivateTemplateAnswer(command, false, e.toString());
        } finally {
            if (secondaryStorage != null) {
                storagePoolMgr.deleteStoragePool(secondaryStorage.getType(), secondaryStorage.getUuid());
            }
        }
    }

};