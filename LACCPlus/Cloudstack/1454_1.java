//,temp,Xenserver625StorageProcessor.java,1030,1173,temp,Xenserver625StorageProcessor.java,780,911
//,3
public class xxx {
    @Override
    public Answer createTemplateFromSnapshot(final CopyCommand cmd) {
        final Connection conn = hypervisorResource.getConnection();

        final DataTO srcData = cmd.getSrcTO();
        final DataTO destData = cmd.getDestTO();

        if (srcData.getDataStore() instanceof PrimaryDataStoreTO && destData.getDataStore() instanceof NfsTO) {
            return createTemplateFromSnapshot2(cmd);
        }

        final int wait = cmd.getWait();

        final SnapshotObjectTO srcObj = (SnapshotObjectTO)srcData;
        final TemplateObjectTO destObj = (TemplateObjectTO)destData;

        final NfsTO srcStore = (NfsTO)srcObj.getDataStore();
        final NfsTO destStore = (NfsTO)destObj.getDataStore();

        URI srcUri = null;
        URI destUri = null;

        try {
            srcUri = new URI(srcStore.getUrl());
            destUri = new URI(destStore.getUrl());
        } catch (final Exception e) {
            s_logger.debug("incorrect url", e);

            return new CopyCmdAnswer("incorrect url" + e.toString());
        }

        final String srcPath = srcObj.getPath();
        final int index = srcPath.lastIndexOf("/");
        final String srcDir = srcPath.substring(0, index);
        final String destDir = destObj.getPath();

        SR srcSr = null;
        SR destSr = null;

        VDI destVdi = null;

        boolean result = false;

        try {
            srcSr = createFileSr(conn, srcUri.getHost() + ":" + srcUri.getPath(), srcDir);

            final String destNfsPath = destUri.getHost() + ":" + destUri.getPath();
            final String localDir = "/var/cloud_mount/" + UUID.nameUUIDFromBytes(destNfsPath.getBytes());

            mountNfs(conn, destUri.getHost() + ":" + destUri.getPath(), localDir);
            makeDirectory(conn, localDir + "/" + destDir);

            destSr = createFileSR(conn, localDir + "/" + destDir);

            final String nameLabel = "cloud-" + UUID.randomUUID().toString();

            final String[] parents = srcObj.getParents();
            final List<VDI> snapshotChains = new ArrayList<VDI>();

            if (parents != null) {
                for (int i = 0; i < parents.length; i++) {
                    final String snChainPath = parents[i];
                    final String uuid = getSnapshotUuid(snChainPath);
                    final VDI chain = VDI.getByUuid(conn, uuid);

                    snapshotChains.add(chain);
                }
            }

            final String snapshotUuid = getSnapshotUuid(srcPath);
            final VDI snapshotVdi = VDI.getByUuid(conn, snapshotUuid);

            snapshotChains.add(snapshotVdi);

            final long templateVirtualSize = snapshotChains.get(0).getVirtualSize(conn);

            destVdi = createVdi(conn, nameLabel, destSr, templateVirtualSize);

            final String destVdiUuid = destVdi.getUuid(conn);

            for (final VDI snapChain : snapshotChains) {
                final Task task = snapChain.copyAsync(conn, null, null, destVdi);
                // poll every 1 seconds ,
                hypervisorResource.waitForTask(conn, task, 1000, wait * 1000);
                hypervisorResource.checkForSuccess(conn, task);

                task.destroy(conn);
            }

            destVdi = VDI.getByUuid(conn, destVdiUuid);

            // scan makes XenServer pick up VDI physicalSize
            destSr.scan(conn);

            final String templateUuid = destVdi.getUuid(conn);
            final String templateFilename = templateUuid + ".vhd";
            final long virtualSize = destVdi.getVirtualSize(conn);
            final long physicalSize = destVdi.getPhysicalUtilisation(conn);

            String templatePath = destNfsPath + "/" + destDir;

            templatePath = templatePath.replaceAll("//", "/");

            result = hypervisorResource.postCreatePrivateTemplate(conn, templatePath, templateFilename, templateUuid, nameLabel, null, physicalSize, virtualSize, destObj.getId());

            if (!result) {
                throw new CloudRuntimeException("Could not create the template.properties file on secondary storage dir");
            }

            final TemplateObjectTO newTemplate = new TemplateObjectTO();

            newTemplate.setPath(destDir + "/" + templateFilename);
            newTemplate.setFormat(Storage.ImageFormat.VHD);
            newTemplate.setSize(destVdi.getVirtualSize(conn));
            newTemplate.setPhysicalSize(destVdi.getPhysicalUtilisation(conn));
            newTemplate.setName(destVdiUuid);

            result = true;

            return new CopyCmdAnswer(newTemplate);
        } catch (final Exception e) {
            s_logger.error("Failed create template from snapshot", e);

            return new CopyCmdAnswer("Failed create template from snapshot " + e.toString());
        } finally {
            if (!result) {
                if (destVdi != null) {
                    try {
                        destVdi.destroy(conn);
                    } catch (final Exception e) {
                        s_logger.debug("Clean up left over on dest storage failed: ", e);
                    }
                }
            }

            if (srcSr != null) {
                hypervisorResource.removeSR(conn, srcSr);
            }

            if (destSr != null) {
                hypervisorResource.removeSR(conn, destSr);
            }
        }
    }

};