//,temp,VmwareStorageProcessor.java,1258,1378,temp,VmwareStorageManagerImpl.java,678,795
//,3
public class xxx {
    private Ternary<String, Long, Long> createTemplateFromSnapshot(long accountId, long templateId, String templateUniqueName, String secStorageUrl, long volumeId,
            String backedUpSnapshotUuid, Integer nfsVersion) throws Exception {

        String secondaryMountPoint = _mountService.getMountPoint(secStorageUrl, nfsVersion);
        String installPath = getTemplateRelativeDirInSecStorage(accountId, templateId);
        String installFullPath = secondaryMountPoint + "/" + installPath;
        String installFullOVAName = installFullPath + "/" + templateUniqueName + ".ova";  //Note: volss for tmpl
        String snapshotRoot = secondaryMountPoint + "/" + getSnapshotRelativeDirInSecStorage(accountId, volumeId);
        String snapshotFullOVAName = snapshotRoot + "/" + backedUpSnapshotUuid + ".ova";
        String snapshotFullOvfName = snapshotRoot + "/" + backedUpSnapshotUuid + ".ovf";
        String result;
        Script command;
        String templateVMDKName = "";
        //String snapshotFullVMDKName = snapshotRoot + "/";
        // the backedUpSnapshotUuid field currently has the format: uuid/uuid. so we need to extract the uuid out
        String backupSSUuid = backedUpSnapshotUuid.substring(0, backedUpSnapshotUuid.indexOf('/'));
        String snapshotFullVMDKName = snapshotRoot + "/" + backupSSUuid + "/";

        synchronized (installPath.intern()) {
            command = new Script(false, "mkdir", _timeout, s_logger);
            command.add("-p");
            command.add(installFullPath);

            result = command.execute();
            if (result != null) {
                String msg = "unable to prepare template directory: " + installPath + ", storage: " + secStorageUrl + ", error msg: " + result;
                s_logger.error(msg);
                throw new Exception(msg);
            }
        }

        try {
            if (new File(snapshotFullOVAName).exists()) {
                command = new Script(false, "cp", _timeout, s_logger);
                command.add(snapshotFullOVAName);
                command.add(installFullOVAName);
                result = command.execute();
                if (result != null) {
                    String msg = "unable to copy snapshot " + snapshotFullOVAName + " to " + installFullPath;
                    s_logger.error(msg);
                    throw new Exception(msg);
                }

                // untar OVA file at template directory
                command = new Script("tar", 0, s_logger);
                command.add("--no-same-owner");
                command.add("-xf", installFullOVAName);
                command.setWorkDir(installFullPath);
                s_logger.info("Executing command: " + command.toString());
                result = command.execute();
                if (result != null) {
                    String msg = "unable to untar snapshot " + snapshotFullOVAName + " to " + installFullPath;
                    s_logger.error(msg);
                    throw new Exception(msg);
                }

            } else {  // there is no ova file, only ovf originally;
                if (new File(snapshotFullOvfName).exists()) {
                    command = new Script(false, "cp", _timeout, s_logger);
                    command.add(snapshotFullOvfName);
                    //command.add(installFullOvfName);
                    command.add(installFullPath);
                    result = command.execute();
                    if (result != null) {
                        String msg = "unable to copy snapshot " + snapshotFullOvfName + " to " + installFullPath;
                        s_logger.error(msg);
                        throw new Exception(msg);
                    }

                    s_logger.info("vmdkfile parent dir: " + snapshotFullVMDKName);
                    File snapshotdir = new File(snapshotFullVMDKName);
                    // File snapshotdir = new File(snapshotRoot);
                    File[] ssfiles = snapshotdir.listFiles();
                    // List<String> filenames = new ArrayList<String>();
                    for (int i = 0; i < ssfiles.length; i++) {
                        String vmdkfile = ssfiles[i].getName();
                        s_logger.info("vmdk file name: " + vmdkfile);
                        if (vmdkfile.toLowerCase().startsWith(backupSSUuid) && vmdkfile.toLowerCase().endsWith(".vmdk")) {
                            snapshotFullVMDKName += vmdkfile;
                            templateVMDKName += vmdkfile;
                            break;
                        }
                    }
                    if (snapshotFullVMDKName != null) {
                        command = new Script(false, "cp", _timeout, s_logger);
                        command.add(snapshotFullVMDKName);
                        command.add(installFullPath);
                        result = command.execute();
                        s_logger.info("Copy VMDK file: " + snapshotFullVMDKName);
                        if (result != null) {
                            String msg = "unable to copy snapshot vmdk file " + snapshotFullVMDKName + " to " + installFullPath;
                            s_logger.error(msg);
                            throw new Exception(msg);
                        }
                    }
                } else {
                    String msg = "unable to find any snapshot ova/ovf files" + snapshotFullOVAName + " to " + installFullPath;
                    s_logger.error(msg);
                    throw new Exception(msg);
                }
            }

            long physicalSize = new File(installFullPath + "/" + templateVMDKName).length();
            OVAProcessor processor = new OVAProcessor();
            // long physicalSize = new File(installFullPath + "/" + templateUniqueName + ".ova").length();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(StorageLayer.InstanceConfigKey, _storage);
            processor.configure("OVA Processor", params);
            long virtualSize = processor.getTemplateVirtualSize(installFullPath, templateUniqueName);

            postCreatePrivateTemplate(installFullPath, templateId, templateUniqueName, physicalSize, virtualSize);
            writeMetaOvaForTemplate(installFullPath, backedUpSnapshotUuid + ".ovf", templateVMDKName, templateUniqueName, physicalSize);
            return new Ternary<String, Long, Long>(installPath + "/" + templateUniqueName + ".ova", physicalSize, virtualSize);
        } catch (Exception e) {
            // TODO, clean up left over files
            throw e;
        }
    }

};