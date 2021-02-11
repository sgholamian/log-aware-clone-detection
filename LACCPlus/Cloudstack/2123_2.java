//,temp,Xenserver625StorageProcessor.java,683,768,temp,XenServerStorageProcessor.java,1319,1394
//,3
public class xxx {
    @Override
    public Answer createTemplateFromVolume(final CopyCommand cmd) {
        final Connection conn = hypervisorResource.getConnection();
        final VolumeObjectTO volume = (VolumeObjectTO) cmd.getSrcTO();
        final TemplateObjectTO template = (TemplateObjectTO) cmd.getDestTO();
        final NfsTO destStore = (NfsTO) cmd.getDestTO().getDataStore();
        final int wait = cmd.getWait();

        final String secondaryStoragePoolURL = destStore.getUrl();
        final String volumeUUID = volume.getPath();

        final String userSpecifiedName = template.getName();

        String details = null;
        SR tmpltSR = null;
        boolean result = false;
        String secondaryStorageMountPath = null;
        String installPath = null;
        try {
            final URI uri = new URI(secondaryStoragePoolURL);
            secondaryStorageMountPath = uri.getHost() + ":" + uri.getPath();
            installPath = template.getPath();
            if (!hypervisorResource.createSecondaryStorageFolder(conn, secondaryStorageMountPath, installPath)) {
                details = " Filed to create folder " + installPath + " in secondary storage";
                s_logger.warn(details);
                return new CopyCmdAnswer(details);
            }

            final VDI vol = getVDIbyUuid(conn, volumeUUID);
            // create template SR
            final URI tmpltURI = new URI(secondaryStoragePoolURL + "/" + installPath);
            tmpltSR = hypervisorResource.createNfsSRbyURI(conn, tmpltURI, false);

            // copy volume to template SR
            final VDI tmpltVDI = hypervisorResource.cloudVDIcopy(conn, vol, tmpltSR, wait);
            // scan makes XenServer pick up VDI physicalSize
            tmpltSR.scan(conn);
            if (userSpecifiedName != null) {
                tmpltVDI.setNameLabel(conn, userSpecifiedName);
            }

            final String tmpltUUID = tmpltVDI.getUuid(conn);
            final String tmpltFilename = tmpltUUID + ".vhd";
            final long virtualSize = tmpltVDI.getVirtualSize(conn);
            final long physicalSize = tmpltVDI.getPhysicalUtilisation(conn);
            // create the template.properties file
            final String templatePath = secondaryStorageMountPath + "/" + installPath;
            result =
                    hypervisorResource.postCreatePrivateTemplate(conn, templatePath, tmpltFilename, tmpltUUID, userSpecifiedName, null, physicalSize, virtualSize,
                            template.getId());
            if (!result) {
                throw new CloudRuntimeException("Could not create the template.properties file on secondary storage dir: " + tmpltURI);
            }
            installPath = installPath + "/" + tmpltFilename;
            hypervisorResource.removeSR(conn, tmpltSR);
            tmpltSR = null;
            final TemplateObjectTO newTemplate = new TemplateObjectTO();
            newTemplate.setPath(installPath);
            newTemplate.setFormat(ImageFormat.VHD);
            newTemplate.setSize(virtualSize);
            newTemplate.setPhysicalSize(physicalSize);
            newTemplate.setName(tmpltUUID);
            final CopyCmdAnswer answer = new CopyCmdAnswer(newTemplate);
            return answer;
        } catch (final Exception e) {
            if (tmpltSR != null) {
                hypervisorResource.removeSR(conn, tmpltSR);
            }
            if (secondaryStorageMountPath != null) {
                hypervisorResource.deleteSecondaryStorageFolder(conn, secondaryStorageMountPath, installPath);
            }
            details = "Creating template from volume " + volumeUUID + " failed due to " + e.toString();
            s_logger.error(details, e);
        }
        return new CopyCmdAnswer(details);
    }

};