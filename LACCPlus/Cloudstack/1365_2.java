//,temp,XenServerStorageProcessor.java,366,428,temp,XenServerStorageProcessor.java,212,275
//,3
public class xxx {
    @Override
    public AttachAnswer attachIso(final AttachCommand cmd) {
        final DiskTO disk = cmd.getDisk();
        final DataTO data = disk.getData();
        final DataStoreTO store = data.getDataStore();

        String isoURL = null;
        if (store == null) {
            final TemplateObjectTO iso = (TemplateObjectTO) disk.getData();
            isoURL = iso.getName();
        } else {
            if (!(store instanceof NfsTO)) {
                s_logger.debug("Can't attach a iso which is not created on nfs: ");
                return new AttachAnswer("Can't attach a iso which is not created on nfs: ");
            }
            final NfsTO nfsStore = (NfsTO) store;
            isoURL = nfsStore.getUrl() + nfsStore.getPathSeparator() + data.getPath();
        }

        final String vmName = cmd.getVmName();
        try {
            final Connection conn = hypervisorResource.getConnection();

            VBD isoVBD = null;

            // Find the VM
            final VM vm = hypervisorResource.getVM(conn, vmName);
            // Find the ISO VDI
            final VDI isoVDI = hypervisorResource.getIsoVDIByURL(conn, vmName, isoURL);

            // Find the VM's CD-ROM VBD
            final Set<VBD> vbds = vm.getVBDs(conn);
            for (final VBD vbd : vbds) {
                final String userDevice = vbd.getUserdevice(conn);
                final Types.VbdType type = vbd.getType(conn);

                if (userDevice.equals("3") && type == Types.VbdType.CD) {
                    isoVBD = vbd;
                    break;
                }
            }

            if (isoVBD == null) {
                throw new CloudRuntimeException("Unable to find CD-ROM VBD for VM: " + vmName);
            } else {
                // If an ISO is already inserted, eject it
                if (!isoVBD.getEmpty(conn)) {
                    isoVBD.eject(conn);
                }

                // Insert the new ISO
                isoVBD.insert(conn, isoVDI);
            }

            return new AttachAnswer(disk);

        } catch (final XenAPIException e) {
            s_logger.warn("Failed to attach iso" + ": " + e.toString(), e);
            return new AttachAnswer(e.toString());
        } catch (final Exception e) {
            s_logger.warn("Failed to attach iso" + ": " + e.toString(), e);
            return new AttachAnswer(e.toString());
        }
    }

};