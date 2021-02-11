//,temp,VmwareStorageProcessor.java,3377,3440,temp,VmwareStorageManagerImpl.java,558,611
//,3
public class xxx {
    private void copyTemplateFromSecondaryToPrimary(VmwareHypervisorHost hyperHost, DatastoreMO datastoreMo, String secondaryStorageUrl, String templatePathAtSecondaryStorage,
            String templateName, String templateUuid, Integer nfsVersion) throws Exception {

        s_logger.info("Executing copyTemplateFromSecondaryToPrimary. secondaryStorage: " + secondaryStorageUrl + ", templatePathAtSecondaryStorage: "
                + templatePathAtSecondaryStorage + ", templateName: " + templateName);

        String secondaryMountPoint = _mountService.getMountPoint(secondaryStorageUrl, nfsVersion);
        s_logger.info("Secondary storage mount point: " + secondaryMountPoint);

        String srcOVAFileName = secondaryMountPoint + "/" + templatePathAtSecondaryStorage + templateName + "." + ImageFormat.OVA.getFileExtension();

        String srcFileName = getOVFFilePath(srcOVAFileName);
        if (srcFileName == null) {
            Script command = new Script("tar", 0, s_logger);
            command.add("--no-same-owner");
            command.add("-xf", srcOVAFileName);
            command.setWorkDir(secondaryMountPoint + "/" + templatePathAtSecondaryStorage);
            s_logger.info("Executing command: " + command.toString());
            String result = command.execute();
            if (result != null) {
                String msg = "Unable to unpack snapshot OVA file at: " + srcOVAFileName;
                s_logger.error(msg);
                throw new Exception(msg);
            }
        }

        srcFileName = getOVFFilePath(srcOVAFileName);
        if (srcFileName == null) {
            String msg = "Unable to locate OVF file in template package directory: " + srcOVAFileName;
            s_logger.error(msg);
            throw new Exception(msg);
        }

        String vmName = templateUuid;
        hyperHost.importVmFromOVF(srcFileName, vmName, datastoreMo, "thin");

        VirtualMachineMO vmMo = hyperHost.findVmOnHyperHost(vmName);
        if (vmMo == null) {
            String msg = "Failed to import OVA template. secondaryStorage: " + secondaryStorageUrl + ", templatePathAtSecondaryStorage: " + templatePathAtSecondaryStorage
                    + ", templateName: " + templateName + ", templateUuid: " + templateUuid;
            s_logger.error(msg);
            throw new Exception(msg);
        }

        if (vmMo.createSnapshot("cloud.template.base", "Base snapshot", false, false)) {
            vmMo.setCustomFieldValue(CustomFieldConstants.CLOUD_UUID, templateUuid);
            vmMo.markAsTemplate();
        } else {
            vmMo.destroy();
            String msg = "Unable to create base snapshot for template, templateName: " + templateName + ", templateUuid: " + templateUuid;
            s_logger.error(msg);
            throw new Exception(msg);
        }
    }

};