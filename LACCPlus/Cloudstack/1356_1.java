//,temp,VmwareStorageProcessor.java,481,552,temp,VmwareStorageManagerImpl.java,869,927
//,3
public class xxx {
    private Pair<VirtualMachineMO, Long> copyTemplateFromSecondaryToPrimary(VmwareHypervisorHost hyperHost, DatastoreMO datastoreMo, String secondaryStorageUrl,
                                                                            String templatePathAtSecondaryStorage, String templateName, String templateUuid,
                                                                            boolean createSnapshot, Integer nfsVersion) throws Exception {
        s_logger.info("Executing copyTemplateFromSecondaryToPrimary. secondaryStorage: " + secondaryStorageUrl + ", templatePathAtSecondaryStorage: " +
                templatePathAtSecondaryStorage + ", templateName: " + templateName);

        String secondaryMountPoint = mountService.getMountPoint(secondaryStorageUrl, nfsVersion);
        s_logger.info("Secondary storage mount point: " + secondaryMountPoint);

        String srcOVAFileName =
                VmwareStorageLayoutHelper.getTemplateOnSecStorageFilePath(secondaryMountPoint, templatePathAtSecondaryStorage, templateName,
                        ImageFormat.OVA.getFileExtension());

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
            String msg =
                    "Failed to import OVA template. secondaryStorage: " + secondaryStorageUrl + ", templatePathAtSecondaryStorage: " + templatePathAtSecondaryStorage +
                            ", templateName: " + templateName + ", templateUuid: " + templateUuid;
            s_logger.error(msg);
            throw new Exception(msg);
        }

        OVAProcessor processor = new OVAProcessor();
        Map<String, Object> params = new HashMap<>();
        params.put(StorageLayer.InstanceConfigKey, _storage);
        processor.configure("OVA Processor", params);
        long virtualSize = processor.getTemplateVirtualSize(secondaryMountPoint + "/" + templatePathAtSecondaryStorage, templateName);

        if (createSnapshot) {
            if (vmMo.createSnapshot("cloud.template.base", "Base snapshot", false, false)) {
                // the same template may be deployed with multiple copies at per-datastore per-host basis,
                // save the original template name from CloudStack DB as the UUID to associate them.
                vmMo.setCustomFieldValue(CustomFieldConstants.CLOUD_UUID, templateName);
                vmMo.markAsTemplate();
            } else {
                vmMo.destroy();

                String msg = "Unable to create base snapshot for template, templateName: " + templateName + ", templateUuid: " + templateUuid;

                s_logger.error(msg);

                throw new Exception(msg);
            }
        }

        return new Pair<>(vmMo, virtualSize);
    }

};