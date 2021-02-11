//,temp,Xenserver625StorageProcessor.java,683,768,temp,XenServerStorageProcessor.java,1396,1518
//,3
public class xxx {
    @Override
    public Answer createTemplateFromSnapshot(final CopyCommand cmd) {
        final Connection conn = hypervisorResource.getConnection();

        final SnapshotObjectTO snapshotObjTO = (SnapshotObjectTO)cmd.getSrcTO();
        final TemplateObjectTO templateObjTO = (TemplateObjectTO)cmd.getDestTO();

        if (!(snapshotObjTO.getDataStore() instanceof PrimaryDataStoreTO) || !(templateObjTO.getDataStore() instanceof NfsTO)) {
            return null;
        }

        final String userSpecifiedTemplateName = templateObjTO.getName();

        NfsTO destStore = null;
        URI destUri = null;

        try {
            destStore = (NfsTO)templateObjTO.getDataStore();

            destUri = new URI(destStore.getUrl());
        } catch (final Exception ex) {
            s_logger.debug("Invalid URI", ex);

            return new CopyCmdAnswer("Invalid URI: " + ex.toString());
        }

        SR srcSr = null;
        SR destSr = null;

        final String destDir = templateObjTO.getPath();
        VDI destVdi = null;

        boolean result = false;

        try {
            final Map<String, String> srcDetails = cmd.getOptions();

            final String iScsiName = srcDetails.get(DiskTO.IQN);
            final String storageHost = srcDetails.get(DiskTO.STORAGE_HOST);
            final String chapInitiatorUsername = srcDetails.get(DiskTO.CHAP_INITIATOR_USERNAME);
            final String chapInitiatorSecret = srcDetails.get(DiskTO.CHAP_INITIATOR_SECRET);

            srcSr = hypervisorResource.getIscsiSR(conn, iScsiName, storageHost, iScsiName, chapInitiatorUsername, chapInitiatorSecret, true);

            final String destNfsPath = destUri.getHost() + ":" + destUri.getPath();

            if (!hypervisorResource.createSecondaryStorageFolder(conn, destNfsPath, destDir)) {
                final String details = " Failed to create folder " + destDir + " in secondary storage";

                s_logger.warn(details);

                return new CopyCmdAnswer(details);
            }

            final URI templateUri = new URI(destStore.getUrl() + "/" + destDir);

            destSr = hypervisorResource.createNfsSRbyURI(conn, templateUri, false);

            // there should only be one VDI in this SR
            final VDI srcVdi = srcSr.getVDIs(conn).iterator().next();

            destVdi = srcVdi.copy(conn, destSr);

            // scan makes XenServer pick up VDI physicalSize
            destSr.scan(conn);

            if (userSpecifiedTemplateName != null) {
                destVdi.setNameLabel(conn, userSpecifiedTemplateName);
            }

            final String templateUuid = destVdi.getUuid(conn);
            final String templateFilename = templateUuid + ".vhd";
            final long virtualSize = destVdi.getVirtualSize(conn);
            final long physicalSize = destVdi.getPhysicalUtilisation(conn);

            // create the template.properties file
            String templatePath = destNfsPath + "/" + destDir;

            templatePath = templatePath.replaceAll("//", "/");

            result = hypervisorResource.postCreatePrivateTemplate(conn, templatePath, templateFilename, templateUuid, userSpecifiedTemplateName, null,
                    physicalSize, virtualSize, templateObjTO.getId());

            if (!result) {
                throw new CloudRuntimeException("Could not create the template.properties file on secondary storage dir: " + templateUri);
            }

            final TemplateObjectTO newTemplate = new TemplateObjectTO();

            newTemplate.setPath(destDir + "/" + templateFilename);
            newTemplate.setFormat(Storage.ImageFormat.VHD);
            newTemplate.setHypervisorType(HypervisorType.XenServer);
            newTemplate.setSize(virtualSize);
            newTemplate.setPhysicalSize(physicalSize);
            newTemplate.setName(templateUuid);

            result = true;

            return new CopyCmdAnswer(newTemplate);
        } catch (final Exception ex) {
            s_logger.error("Failed to create a template from a snapshot", ex);

            return new CopyCmdAnswer("Failed to create a template from a snapshot: " + ex.toString());
        } finally {
            if (!result) {
                if (destVdi != null) {
                    try {
                        destVdi.destroy(conn);
                    } catch (final Exception e) {
                        s_logger.debug("Cleaned up leftover VDI on destination storage due to failure: ", e);
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