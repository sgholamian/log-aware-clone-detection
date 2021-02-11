//,temp,VmwareStorageManagerImpl.java,937,983,temp,VmwareStorageManagerImpl.java,613,676
//,3
public class xxx {
    private Ternary<String, Long, Long> createTemplateFromVolume(VirtualMachineMO vmMo, long accountId, long templateId, String templateUniqueName, String secStorageUrl,
            String volumePath, String workerVmName, Integer nfsVersion) throws Exception {

        String secondaryMountPoint = _mountService.getMountPoint(secStorageUrl, nfsVersion);
        String installPath = getTemplateRelativeDirInSecStorage(accountId, templateId);
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
            vmMo.cloneFromCurrentSnapshot(workerVmName, 0, 4, volumeDeviceInfo.second(), VmwareHelper.getDiskDeviceDatastore(volumeDeviceInfo.first()));
            clonedVm = vmMo.getRunningHost().findVmOnHyperHost(workerVmName);
            if (clonedVm == null) {
                String msg = "Unable to create dummy VM to export volume. volume path: " + volumePath;
                s_logger.error(msg);
                throw new Exception(msg);
            }

            clonedVm.exportVm(secondaryMountPoint + "/" + installPath, templateUniqueName, true, false);

            long physicalSize = new File(installFullPath + "/" + templateUniqueName + ".ova").length();
            OVAProcessor processor = new OVAProcessor();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(StorageLayer.InstanceConfigKey, _storage);
            processor.configure("OVA Processor", params);
            long virtualSize = processor.getTemplateVirtualSize(installFullPath, templateUniqueName);

            postCreatePrivateTemplate(installFullPath, templateId, templateUniqueName, physicalSize, virtualSize);
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