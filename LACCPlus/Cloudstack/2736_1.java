//,temp,VmwareStorageProcessor.java,2107,2189,temp,VmwareResource.java,4654,4736
//,3
public class xxx {
    private Answer attachIso(DiskTO disk, boolean isAttach, String vmName) {
        try {
            VmwareContext context = hostService.getServiceContext(null);
            VmwareHypervisorHost hyperHost = hostService.getHyperHost(context, null);
            VirtualMachineMO vmMo = hyperHost.findVmOnHyperHost(vmName);
            if (vmMo == null) {
                String msg = "Unable to find VM in vSphere to execute AttachIsoCommand, vmName: " + vmName;
                s_logger.error(msg);
                throw new Exception(msg);
            }
            TemplateObjectTO iso = (TemplateObjectTO)disk.getData();
            NfsTO nfsImageStore = (NfsTO)iso.getDataStore();
            String storeUrl = null;
            if (nfsImageStore != null) {
                storeUrl = nfsImageStore.getUrl();
            }
            if (storeUrl == null) {
                if (!iso.getName().equalsIgnoreCase("vmware-tools.iso")) {
                    String msg = "ISO store root url is not found in AttachIsoCommand";
                    s_logger.error(msg);
                    throw new Exception(msg);
                } else {
                    if (isAttach) {
                        vmMo.mountToolsInstaller();
                    } else {
                        try{
                            if (!vmMo.unmountToolsInstaller()) {
                                return new AttachAnswer("Failed to unmount vmware-tools installer ISO as the corresponding CDROM device is locked by VM. Please unmount the CDROM device inside the VM and ret-try.");
                            }
                        } catch(Throwable e){
                            vmMo.detachIso(null);
                        }
                    }

                    return new AttachAnswer(disk);
                }
            }

            ManagedObjectReference morSecondaryDs = prepareSecondaryDatastoreOnHost(storeUrl);
            String isoPath = nfsImageStore.getUrl() + File.separator + iso.getPath();
            if (!isoPath.startsWith(storeUrl)) {
                assert (false);
                String msg = "ISO path does not start with the secondary storage root";
                s_logger.error(msg);
                throw new Exception(msg);
            }

            int isoNameStartPos = isoPath.lastIndexOf('/');
            String isoFileName = isoPath.substring(isoNameStartPos + 1);
            String isoStorePathFromRoot = isoPath.substring(storeUrl.length() + 1, isoNameStartPos);

            // TODO, check if iso is already attached, or if there is a previous
            // attachment
            DatastoreMO secondaryDsMo = new DatastoreMO(context, morSecondaryDs);
            String storeName = secondaryDsMo.getName();
            String isoDatastorePath = String.format("[%s] %s/%s", storeName, isoStorePathFromRoot, isoFileName);

            if (isAttach) {
                vmMo.attachIso(isoDatastorePath, morSecondaryDs, true, false);
            } else {
                vmMo.detachIso(isoDatastorePath);
            }

            return new AttachAnswer(disk);
        } catch (Throwable e) {
            if (e instanceof RemoteException) {
                s_logger.warn("Encounter remote exception to vCenter, invalidate VMware session context");
                hostService.invalidateServiceContext(null);
            }

            if (isAttach) {
                String msg = "AttachIsoCommand(attach) failed due to " + VmwareHelper.getExceptionMessage(e);
                msg = msg + " Also check if your guest os is a supported version";
                s_logger.error(msg, e);
                return new AttachAnswer(msg);
            } else {
                String msg = "AttachIsoCommand(detach) failed due to " + VmwareHelper.getExceptionMessage(e);
                msg = msg + " Also check if your guest os is a supported version";
                s_logger.warn(msg, e);
                return new AttachAnswer(msg);
            }
        }
    }

};