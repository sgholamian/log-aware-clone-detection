//,temp,VmwareStorageProcessor.java,2107,2189,temp,VmwareResource.java,4654,4736
//,3
public class xxx {
    protected AttachIsoAnswer execute(AttachIsoCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource AttachIsoCommand: " + _gson.toJson(cmd));
        }

        try {
            VmwareHypervisorHost hyperHost = getHyperHost(getServiceContext());
            VirtualMachineMO vmMo = hyperHost.findVmOnHyperHost(cmd.getVmName());
            if (vmMo == null) {
                String msg = "Unable to find VM in vSphere to execute AttachIsoCommand, vmName: " + cmd.getVmName();
                s_logger.error(msg);
                throw new Exception(msg);
            }

            String storeUrl = cmd.getStoreUrl();
            if (storeUrl == null) {
                if (!cmd.getIsoPath().equalsIgnoreCase("vmware-tools.iso")) {
                    String msg = "ISO store root url is not found in AttachIsoCommand";
                    s_logger.error(msg);
                    throw new Exception(msg);
                } else {
                    if (cmd.isAttach()) {
                        vmMo.mountToolsInstaller();
                    } else {
                        try {
                            if (!vmMo.unmountToolsInstaller()) {
                                return new AttachIsoAnswer(cmd, false,
                                        "Failed to unmount vmware-tools installer ISO as the corresponding CDROM device is locked by VM. Please unmount the CDROM device inside the VM and ret-try.");
                            }
                        } catch (Throwable e) {
                            vmMo.detachIso(null);
                        }
                    }

                    return new AttachIsoAnswer(cmd);
                }
            }

            ManagedObjectReference morSecondaryDs = prepareSecondaryDatastoreOnHost(storeUrl);
            String isoPath = cmd.getIsoPath();
            if (!isoPath.startsWith(storeUrl)) {
                assert (false);
                String msg = "ISO path does not start with the secondary storage root";
                s_logger.error(msg);
                throw new Exception(msg);
            }

            int isoNameStartPos = isoPath.lastIndexOf('/');
            String isoFileName = isoPath.substring(isoNameStartPos + 1);
            String isoStorePathFromRoot = isoPath.substring(storeUrl.length() + 1, isoNameStartPos + 1);


            // TODO, check if iso is already attached, or if there is a previous
            // attachment
            DatastoreMO secondaryDsMo = new DatastoreMO(getServiceContext(), morSecondaryDs);
            String storeName = secondaryDsMo.getName();
            String isoDatastorePath = String.format("[%s] %s%s", storeName, isoStorePathFromRoot, isoFileName);

            if (cmd.isAttach()) {
                vmMo.attachIso(isoDatastorePath, morSecondaryDs, true, false, cmd.getDeviceKey());
                return new AttachIsoAnswer(cmd);
            } else {
                int key = vmMo.detachIso(isoDatastorePath, cmd.isForce());
                return new AttachIsoAnswer(cmd, key);
            }

        } catch (Throwable e) {
            if (e instanceof RemoteException) {
                s_logger.warn("Encounter remote exception to vCenter, invalidate VMware session context");
                invalidateServiceContext();
            }

            if (cmd.isAttach()) {
                String msg = "AttachIsoCommand(attach) failed due to " + VmwareHelper.getExceptionMessage(e);
                s_logger.error(msg, e);
                return new AttachIsoAnswer(cmd, false, msg);
            } else {
                String msg = "AttachIsoCommand(detach) failed due to " + VmwareHelper.getExceptionMessage(e);
                s_logger.warn(msg, e);
                return new AttachIsoAnswer(cmd, false, msg);
            }
        }
    }

};