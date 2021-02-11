//,temp,Xenserver625StorageProcessor.java,249,412,temp,XenServerStorageProcessor.java,652,779
//,3
public class xxx {
    @Override
    public Answer copyTemplateToPrimaryStorage(final CopyCommand cmd) {
        final DataTO srcDataTo = cmd.getSrcTO();
        final DataTO destDataTo = cmd.getDestTO();
        final int wait = cmd.getWait();
        final DataStoreTO srcDataStoreTo = srcDataTo.getDataStore();
        final Connection conn = hypervisorResource.getConnection();
        SR sr = null;
        boolean removeSrAfterCopy = false;

        try {
            if (srcDataStoreTo instanceof NfsTO && srcDataTo.getObjectType() == DataObjectType.TEMPLATE) {
                final NfsTO srcImageStore = (NfsTO) srcDataStoreTo;
                final TemplateObjectTO srcTemplateObjectTo = (TemplateObjectTO) srcDataTo;
                final String storeUrl = srcImageStore.getUrl();
                final URI uri = new URI(storeUrl);
                final String tmplPath = uri.getHost() + ":" + uri.getPath() + "/" + srcDataTo.getPath();
                final DataStoreTO destDataStoreTo = destDataTo.getDataStore();

                boolean managed = false;
                String storageHost = null;
                String managedStoragePoolName = null;
                String managedStoragePoolRootVolumeName = null;
                String managedStoragePoolRootVolumeSize = null;
                String chapInitiatorUsername = null;
                String chapInitiatorSecret = null;

                if (destDataStoreTo instanceof PrimaryDataStoreTO) {
                    final PrimaryDataStoreTO destPrimaryDataStoreTo = (PrimaryDataStoreTO)destDataStoreTo;

                    final Map<String, String> details = destPrimaryDataStoreTo.getDetails();

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
                }

                if (managed) {
                    final Map<String, String> details = new HashMap<String, String>();

                    details.put(DiskTO.STORAGE_HOST, storageHost);
                    details.put(DiskTO.IQN, managedStoragePoolName);
                    details.put(DiskTO.VOLUME_SIZE, managedStoragePoolRootVolumeSize);
                    details.put(DiskTO.CHAP_INITIATOR_USERNAME, chapInitiatorUsername);
                    details.put(DiskTO.CHAP_INITIATOR_SECRET, chapInitiatorSecret);

                    sr = hypervisorResource.prepareManagedSr(conn, details);
                } else {
                    final String srName = destDataStoreTo.getUuid();
                    final Set<SR> srs = SR.getByNameLabel(conn, srName);

                    if (srs.size() != 1) {
                        final String msg = "There are " + srs.size() + " SRs with same name: " + srName;

                        s_logger.warn(msg);

                        return new CopyCmdAnswer(msg);
                    } else {
                        sr = srs.iterator().next();
                    }
                }

                final String srUuid = sr.getUuid(conn);
                final String tmplUuid = copy_vhd_from_secondarystorage(conn, tmplPath, srUuid, wait);
                final VDI tmplVdi = getVDIbyUuid(conn, tmplUuid);

                final String uuidToReturn;
                final Long physicalSize = tmplVdi.getPhysicalUtilisation(conn);

                if (managed) {
                    uuidToReturn = tmplUuid;

                    tmplVdi.setNameLabel(conn, managedStoragePoolRootVolumeName);
                } else {
                    final VDI snapshotVdi = tmplVdi.snapshot(conn, new HashMap<String, String>());

                    uuidToReturn = snapshotVdi.getUuid(conn);

                    snapshotVdi.setNameLabel(conn, "Template " + srcTemplateObjectTo.getName());

                    tmplVdi.destroy(conn);
                }

                sr.scan(conn);

                try {
                    Thread.sleep(5000);
                } catch (final InterruptedException e) {
                }

                final TemplateObjectTO newVol = new TemplateObjectTO();

                newVol.setUuid(uuidToReturn);
                newVol.setPath(uuidToReturn);

                if (physicalSize != null) {
                    newVol.setSize(physicalSize);
                }

                newVol.setFormat(ImageFormat.VHD);

                return new CopyCmdAnswer(newVol);
            }
        } catch (final Exception e) {
            final String msg = "Catch Exception " + e.getClass().getName() + " for template + " + " due to " + e.toString();

            s_logger.warn(msg, e);

            return new CopyCmdAnswer(msg);
        }
        finally {
            if (removeSrAfterCopy && sr != null) {
                hypervisorResource.removeSR(conn, sr);
            }
        }

        return new CopyCmdAnswer("not implemented yet");
    }

};