//,temp,Xenserver625StorageProcessor.java,1175,1297,temp,Xenserver625StorageProcessor.java,1030,1173
//,3
public class xxx {
    private Answer createTemplateFromSnapshot2(final CopyCommand cmd) {
        final Connection conn = hypervisorResource.getConnection();

        final SnapshotObjectTO snapshotObjTO = (SnapshotObjectTO)cmd.getSrcTO();
        final TemplateObjectTO templateObjTO = (TemplateObjectTO)cmd.getDestTO();

        if (!(snapshotObjTO.getDataStore() instanceof PrimaryDataStoreTO) || !(templateObjTO.getDataStore() instanceof NfsTO)) {
            return null;
        }

        NfsTO destStore;
        URI destUri;

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
            String srType;

            srType = CitrixResourceBase.SRType.LVMOISCSI.toString();

            srcSr = hypervisorResource.getIscsiSR(conn, iScsiName, storageHost, iScsiName, chapInitiatorUsername, chapInitiatorSecret, false, srType, true);

            final String destNfsPath = destUri.getHost() + ":" + destUri.getPath();
            final String localDir = "/var/cloud_mount/" + UUID.nameUUIDFromBytes(destNfsPath.getBytes());

            mountNfs(conn, destNfsPath, localDir);
            makeDirectory(conn, localDir + "/" + destDir);

            destSr = createFileSR(conn, localDir + "/" + destDir);

            // there should only be one VDI in this SR
            final VDI srcVdi = srcSr.getVDIs(conn).iterator().next();

            destVdi = srcVdi.copy(conn, destSr);

            final String nameLabel = "cloud-" + UUID.randomUUID().toString();

            destVdi.setNameLabel(conn, nameLabel);

            // scan makes XenServer pick up VDI physicalSize
            destSr.scan(conn);

            final String templateUuid = destVdi.getUuid(conn);
            final String templateFilename = templateUuid + ".vhd";
            final long virtualSize = destVdi.getVirtualSize(conn);
            final long physicalSize = destVdi.getPhysicalUtilisation(conn);

            // create the template.properties file
            String templatePath = destNfsPath + "/" + destDir;

            templatePath = templatePath.replaceAll("//", "/");

            result = hypervisorResource.postCreatePrivateTemplate(conn, templatePath, templateFilename, templateUuid, nameLabel, null, physicalSize, virtualSize, templateObjTO.getId());

            if (!result) {
                throw new CloudRuntimeException("Could not create the template.properties file on secondary storage dir");
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
        } catch (final BadServerResponse e) {
            s_logger.error("Failed to create a template from a snapshot due to incomprehensible server response", e);

            return new CopyCmdAnswer("Failed to create a template from a snapshot: " + e.toString());
        } catch (final XenAPIException e) {
            s_logger.error("Failed to create a template from a snapshot due to xenapi error", e);

            return new CopyCmdAnswer("Failed to create a template from a snapshot: " + e.toString());
        } catch (final XmlRpcException e) {
            s_logger.error("Failed to create a template from a snapshot due to rpc error", e);

            return new CopyCmdAnswer("Failed to create a template from a snapshot: " + e.toString());
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