//,temp,XenServerStorageProcessor.java,366,428,temp,CitrixAttachIsoCommandWrapper.java,45,135
//,3
public class xxx {
    @Override
    public Answer execute(final AttachIsoCommand command, final CitrixResourceBase citrixResourceBase) {
        final Connection conn = citrixResourceBase.getConnection();
        final boolean attach = command.isAttach();
        final String vmName = command.getVmName();
        final String isoURL = command.getIsoPath();

        String errorMsg;
        if (attach) {
            errorMsg = "Failed to attach ISO";
        } else {
            errorMsg = "Failed to detach ISO";
        }
        try {
            if (attach) {
                VBD isoVBD = null;

                // Find the VM
                final VM vm = citrixResourceBase.getVM(conn, vmName);

                // Find the ISO VDI
                final VDI isoVDI = citrixResourceBase.getIsoVDIByURL(conn, vmName, isoURL);

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
                    if (isoVBD.getEmpty(conn) == false) {
                        isoVBD.eject(conn);
                    }

                    // Insert the new ISO
                    isoVBD.insert(conn, isoVDI);
                }

                return new Answer(command);
            } else {
                // Find the VM
                final VM vm = citrixResourceBase.getVM(conn, vmName);
                final String vmUUID = vm.getUuid(conn);

                // Find the ISO VDI
                final VDI isoVDI = citrixResourceBase.getIsoVDIByURL(conn, vmName, isoURL);

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
                    citrixResourceBase.removeSR(conn, sr);
                }

                return new Answer(command);
            }
        } catch (final XenAPIException e) {
            s_logger.warn(errorMsg + ": " + e.toString(), e);
            return new Answer(command, false, e.toString());
        } catch (final Exception e) {
            s_logger.warn(errorMsg + ": " + e.toString(), e);
            return new Answer(command, false, e.getMessage());
        }
    }

};