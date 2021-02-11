//,temp,Xenserver625StorageProcessor.java,249,412,temp,XenServerStorageProcessor.java,652,779
//,3
public class xxx {
    @Override
    public Answer copyTemplateToPrimaryStorage(final CopyCommand cmd) {
        final DataTO srcData = cmd.getSrcTO();
        final DataTO destData = cmd.getDestTO();
        final int wait = cmd.getWait();
        final DataStoreTO srcStore = srcData.getDataStore();
        final Connection conn = hypervisorResource.getConnection();
        SR srcSr = null;
        SR destSr = null;
        boolean removeSrAfterCopy = false;
        Task task = null;

        try {
            if (srcStore instanceof NfsTO && srcData.getObjectType() == DataObjectType.TEMPLATE) {
                final NfsTO srcImageStore = (NfsTO)srcStore;
                final TemplateObjectTO srcTemplate = (TemplateObjectTO)srcData;
                final String storeUrl = srcImageStore.getUrl();
                final URI uri = new URI(storeUrl);
                String volumePath = srcData.getPath();

                volumePath = StringUtils.stripEnd(volumePath, "/");

                final String[] splits = volumePath.split("/");
                String volumeDirectory = volumePath;

                if (splits.length > 4) {
                    // "template/tmpl/dcid/templateId/templatename"
                    final int index = volumePath.lastIndexOf("/");

                    volumeDirectory = volumePath.substring(0, index);
                }

                srcSr = createFileSr(conn, uri.getHost() + ":" + uri.getPath(), volumeDirectory);

                final Set<VDI> setVdis = srcSr.getVDIs(conn);

                if (setVdis.size() != 1) {
                    return new CopyCmdAnswer("Expected 1 VDI template, but found " + setVdis.size() + " VDI templates on: " + uri.getHost() + ":" + uri.getPath() + "/" + volumeDirectory);
                }

                final VDI srcVdi = setVdis.iterator().next();

                boolean managed = false;
                String storageHost = null;
                String managedStoragePoolName = null;
                String managedStoragePoolRootVolumeName = null;
                String managedStoragePoolRootVolumeSize = null;
                String chapInitiatorUsername = null;
                String chapInitiatorSecret = null;

                final PrimaryDataStoreTO destStore = (PrimaryDataStoreTO)destData.getDataStore();

                Map<String, String> details = destStore.getDetails();

                if (details != null) {
                    managed = Boolean.parseBoolean(details.get(PrimaryDataStoreTO.MANAGED));

                    if (managed) {
                        storageHost = details.get(PrimaryDataStoreTO.STORAGE_HOST);
                        managedStoragePoolName = details.get(PrimaryDataStoreTO.MANAGED_STORE_TARGET);
                        managedStoragePoolRootVolumeName = details.get(PrimaryDataStoreTO.MANAGED_STORE_TARGET_ROOT_VOLUME);
                        managedStoragePoolRootVolumeSize = details.get(PrimaryDataStoreTO.VOLUME_SIZE);
                        chapInitiatorUsername = details.get(PrimaryDataStoreTO.CHAP_INITIATOR_USERNAME);
                        chapInitiatorSecret = details.get(PrimaryDataStoreTO.CHAP_INITIATOR_SECRET);
                        removeSrAfterCopy = Boolean.parseBoolean(details.get(PrimaryDataStoreTO.REMOVE_AFTER_COPY));
                    }
                }

                if (managed) {
                    details = new HashMap<String, String>();

                    details.put(DiskTO.STORAGE_HOST, storageHost);
                    details.put(DiskTO.IQN, managedStoragePoolName);
                    details.put(DiskTO.VOLUME_SIZE, managedStoragePoolRootVolumeSize);
                    details.put(DiskTO.CHAP_INITIATOR_USERNAME, chapInitiatorUsername);
                    details.put(DiskTO.CHAP_INITIATOR_SECRET, chapInitiatorSecret);

                    destSr = hypervisorResource.prepareManagedSr(conn, details);
                } else {
                    final String srName = destStore.getUuid();
                    final Set<SR> srs = SR.getByNameLabel(conn, srName);

                    if (srs.size() != 1) {
                        final String msg = "There are " + srs.size() + " SRs with same name: " + srName;

                        s_logger.warn(msg);

                        return new CopyCmdAnswer(msg);
                    } else {
                        destSr = srs.iterator().next();
                    }
                }

                task = srcVdi.copyAsync(conn, destSr, null, null);

                // poll every 1 seconds ,
                hypervisorResource.waitForTask(conn, task, 1000, wait * 1000);
                hypervisorResource.checkForSuccess(conn, task);

                final VDI tmplVdi = Types.toVDI(task, conn);

                final String uuidToReturn;
                final Long physicalSize = tmplVdi.getPhysicalUtilisation(conn);

                if (managed) {
                    uuidToReturn = tmplVdi.getUuid(conn);

                    tmplVdi.setNameLabel(conn, managedStoragePoolRootVolumeName);
                } else {
                    final VDI snapshotVdi = tmplVdi.snapshot(conn, new HashMap<String, String>());

                    uuidToReturn = snapshotVdi.getUuid(conn);

                    snapshotVdi.setNameLabel(conn, "Template " + srcTemplate.getName());

                    tmplVdi.destroy(conn);
                }

                destSr.scan(conn);

                try {
                    Thread.sleep(5000);
                } catch (final Exception e) {
                }

                final TemplateObjectTO newVol = new TemplateObjectTO();

                newVol.setUuid(uuidToReturn);
                newVol.setPath(uuidToReturn);

                if (physicalSize != null) {
                    newVol.setSize(physicalSize);
                }

                newVol.setFormat(Storage.ImageFormat.VHD);

                return new CopyCmdAnswer(newVol);
            }
        } catch (final Exception e) {
            final String msg = "Catch Exception " + e.getClass().getName() + " for template due to " + e.toString();

            s_logger.warn(msg, e);

            return new CopyCmdAnswer(msg);
        } finally {
            if (task != null) {
                try {
                    task.destroy(conn);
                } catch (final Exception e) {
                    s_logger.debug("unable to destroy task (" + task.toWireString() + ") due to " + e.toString());
                }
            }

            if (srcSr != null) {
                hypervisorResource.removeSR(conn, srcSr);
            }

            if (removeSrAfterCopy && destSr != null) {
                hypervisorResource.removeSR(conn, destSr);
            }
        }

        return new CopyCmdAnswer("not implemented yet");
    }

};