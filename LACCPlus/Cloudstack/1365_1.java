//,temp,XenServerStorageProcessor.java,366,428,temp,XenServerStorageProcessor.java,212,275
//,3
public class xxx {
    @Override
    public Answer dettachIso(final DettachCommand cmd) {
        final DiskTO disk = cmd.getDisk();
        final DataTO data = disk.getData();
        final DataStoreTO store = data.getDataStore();

        String isoURL = null;
        if (store == null) {
            final TemplateObjectTO iso = (TemplateObjectTO) disk.getData();
            isoURL = iso.getName();
        } else {
            if (!(store instanceof NfsTO)) {
                s_logger.debug("Can't detach a iso which is not created on nfs: ");
                return new AttachAnswer("Can't detach a iso which is not created on nfs: ");
            }
            final NfsTO nfsStore = (NfsTO) store;
            isoURL = nfsStore.getUrl() + nfsStore.getPathSeparator() + data.getPath();
        }

        try {
            final Connection conn = hypervisorResource.getConnection();
            // Find the VM
            final VM vm = hypervisorResource.getVM(conn, cmd.getVmName());
            final String vmUUID = vm.getUuid(conn);

            // Find the ISO VDI
            final VDI isoVDI = hypervisorResource.getIsoVDIByURL(conn, cmd.getVmName(), isoURL);

            final SR sr = isoVDI.getSR(conn);

            // Look up all VBDs for this VDI
            final Set<VBD> vbds = isoVDI.getVBDs(conn);

            // Iterate through VBDs, and if the VBD belongs the VM, eject
            // the ISO from it
            for (final VBD vbd : vbds) {
                final VM vbdVM = vbd.getVM(conn);
                final String vbdVmUUID = vbdVM.getUuid(conn);

                if (vbdVmUUID.equals(vmUUID)) {
                    // If an ISO is already inserted, eject it
                    if (!vbd.getEmpty(conn)) {
                        vbd.eject(conn);
                    }
                    break;
                }
            }

            if (!XenServerUtilitiesHelper.isXenServerToolsSR(sr.getNameLabel(conn))) {
                hypervisorResource.removeSR(conn, sr);
            }

            return new DettachAnswer(disk);
        } catch (final XenAPIException e) {
            final String msg = "Failed to detach volume" + " for uuid: " + data.getPath() + "  due to " + e.toString();
            s_logger.warn(msg, e);
            return new DettachAnswer(msg);
        } catch (final Exception e) {
            final String msg = "Failed to detach volume" + " for uuid: " + data.getPath() + "  due to " + e.getMessage();
            s_logger.warn(msg, e);
            return new DettachAnswer(msg);
        }
    }

};