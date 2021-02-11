//,temp,KVMStorageProcessor.java,664,786,temp,KVMStorageProcessor.java,525,650
//,3
public class xxx {
    private Answer createTemplateFromVolumeOrSnapshot(CopyCommand cmd) {
        DataTO srcData = cmd.getSrcTO();

        final boolean isVolume;

        if (srcData instanceof VolumeObjectTO) {
            isVolume = true;
        }
        else if (srcData instanceof SnapshotObjectTO) {
            isVolume = false;
        }
        else {
            return new CopyCmdAnswer("unsupported object type");
        }

        PrimaryDataStoreTO primaryStore = (PrimaryDataStoreTO)srcData.getDataStore();

        DataTO destData = cmd.getDestTO();
        TemplateObjectTO template = (TemplateObjectTO)destData;
        DataStoreTO imageStore = template.getDataStore();

        if (!(imageStore instanceof NfsTO)) {
            return new CopyCmdAnswer("unsupported protocol");
        }

        NfsTO nfsImageStore = (NfsTO)imageStore;

        KVMStoragePool secondaryStorage = null;

        try {
            Map<String, String> details = cmd.getOptions();

            String path = details != null ? details.get(DiskTO.IQN) : null;

            if (path == null) {
                new CloudRuntimeException("The 'path' field must be specified.");
            }

            storagePoolMgr.connectPhysicalDisk(primaryStore.getPoolType(), primaryStore.getUuid(), path, details);

            KVMPhysicalDisk srcDisk = storagePoolMgr.getPhysicalDisk(primaryStore.getPoolType(), primaryStore.getUuid(), path);

            secondaryStorage = storagePoolMgr.getStoragePoolByURI(nfsImageStore.getUrl());

            String templateFolder = template.getPath();
            String tmpltPath = secondaryStorage.getLocalPath() + File.separator + templateFolder;

            storageLayer.mkdirs(tmpltPath);

            String templateName = UUID.randomUUID().toString();

            s_logger.debug("Converting " + srcDisk.getFormat().toString() + " disk " + srcDisk.getPath() + " into template " + templateName);

            String destName = templateFolder + "/" + templateName + ".qcow2";

            storagePoolMgr.copyPhysicalDisk(srcDisk, destName, secondaryStorage, cmd.getWaitInMillSeconds());

            File templateProp = new File(tmpltPath + "/template.properties");

            if (!templateProp.exists()) {
                templateProp.createNewFile();
            }

            String templateContent = "filename=" + templateName + ".qcow2" + System.getProperty("line.separator");

            DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy");
            Date date = new Date();

            if (isVolume) {
                templateContent += "volume.name=" + dateFormat.format(date) + System.getProperty("line.separator");
            }
            else {
                templateContent += "snapshot.name=" + dateFormat.format(date) + System.getProperty("line.separator");
            }

            FileOutputStream templFo = new FileOutputStream(templateProp);

            templFo.write(templateContent.getBytes());
            templFo.flush();
            templFo.close();

            Map<String, Object> params = new HashMap<>();

            params.put(StorageLayer.InstanceConfigKey, storageLayer);

            Processor qcow2Processor = new QCOW2Processor();

            qcow2Processor.configure("QCOW2 Processor", params);

            FormatInfo info = qcow2Processor.process(tmpltPath, null, templateName);

            TemplateLocation loc = new TemplateLocation(storageLayer, tmpltPath);

            loc.create(1, true, templateName);
            loc.addFormat(info);
            loc.save();

            storagePoolMgr.disconnectPhysicalDisk(primaryStore.getPoolType(), primaryStore.getUuid(), path);

            TemplateObjectTO newTemplate = new TemplateObjectTO();

            newTemplate.setPath(templateFolder + File.separator + templateName + ".qcow2");
            newTemplate.setSize(info.virtualSize);
            newTemplate.setPhysicalSize(info.size);
            newTemplate.setFormat(ImageFormat.QCOW2);
            newTemplate.setName(templateName);

            return new CopyCmdAnswer(newTemplate);
        } catch (Exception ex) {
            if (isVolume) {
                s_logger.debug("Failed to create template from volume: ", ex);
            }
            else {
                s_logger.debug("Failed to create template from snapshot: ", ex);
            }

            return new CopyCmdAnswer(ex.toString());
        } finally {
            if (secondaryStorage != null) {
                secondaryStorage.delete();
            }
        }
    }

};