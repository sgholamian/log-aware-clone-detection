//,temp,VmwareStorageProcessor.java,1093,1166,temp,VmwareStorageManagerImpl.java,613,676
//,3
public class xxx {
    private Ternary<String, Long, Long> createTemplateFromVolume(VirtualMachineMO vmMo, String installPath, long templateId, String templateUniqueName,
                                                                 String secStorageUrl, String volumePath, String workerVmName, Integer nfsVersion) throws Exception {

        String secondaryMountPoint = mountService.getMountPoint(secStorageUrl, nfsVersion);
        String installFullPath = secondaryMountPoint + "/" + installPath;
        synchronized (installPath.intern()) {
            Script command = new Script(false, "mkdir", _timeout, s_logger);
            command.add("-p");
            command.add(installFullPath);

            String result = command.execute();
            if (result != null) {
                String msg = "unable to prepare template directory: " + installPath + ", storage: " + secStorageUrl + ", error msg: " + result;
                s_logger.error(msg);
                throw new Exception(msg);
            }
        }

        VirtualMachineMO clonedVm = null;
        try {
            Pair<VirtualDisk, String> volumeDeviceInfo = vmMo.getDiskDevice(volumePath);
            if (volumeDeviceInfo == null) {
                String msg = "Unable to find related disk device for volume. volume path: " + volumePath;
                s_logger.error(msg);
                throw new Exception(msg);
            }

            if (!vmMo.createSnapshot(templateUniqueName, "Temporary snapshot for template creation", false, false)) {
                String msg = "Unable to take snapshot for creating template from volume. volume path: " + volumePath;
                s_logger.error(msg);
                throw new Exception(msg);
            }

            // 4 MB is the minimum requirement for VM memory in VMware
            Pair<VirtualMachineMO, String[]> cloneResult =
                    vmMo.cloneFromCurrentSnapshot(workerVmName, 0, 4, volumeDeviceInfo.second(), VmwareHelper.getDiskDeviceDatastore(volumeDeviceInfo.first()));
            clonedVm = cloneResult.first();

            clonedVm.exportVm(secondaryMountPoint + "/" + installPath, templateUniqueName, false, false);

            // Get VMDK filename
            String templateVMDKName = "";
            File[] files = new File(installFullPath).listFiles();
            if(files != null) {
                for(File file : files) {
                    String fileName = file.getName();
                    if(fileName.toLowerCase().startsWith(templateUniqueName) && fileName.toLowerCase().endsWith(".vmdk")) {
                        templateVMDKName += fileName;
                        break;
                    }
                }
            }

            long physicalSize = new File(installFullPath + "/" + templateVMDKName).length();
            OVAProcessor processor = new OVAProcessor();

            Map<String, Object> params = new HashMap<>();
            params.put(StorageLayer.InstanceConfigKey, _storage);
            processor.configure("OVA Processor", params);
            long virtualSize = processor.getTemplateVirtualSize(installFullPath, templateUniqueName);

            postCreatePrivateTemplate(installFullPath, templateId, templateUniqueName, physicalSize, virtualSize);
            writeMetaOvaForTemplate(installFullPath, templateUniqueName + ".ovf", templateVMDKName, templateUniqueName, physicalSize);
            return new Ternary<String, Long, Long>(installPath + "/" + templateUniqueName + ".ova", physicalSize, virtualSize);

        } finally {
            if (clonedVm != null) {
                clonedVm.detachAllDisks();
                clonedVm.destroy();
            }

            vmMo.removeSnapshot(templateUniqueName, false);
        }
    }

};