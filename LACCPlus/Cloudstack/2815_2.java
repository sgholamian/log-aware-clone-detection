//,temp,CitrixResourceBase.java,5536,5604,temp,CitrixResourceBase.java,5373,5446
//,3
public class xxx {
    public boolean attachConfigDriveIsoToVm(final Connection conn, final VM vm) throws XenAPIException, XmlRpcException {

        final String vmName = vm.getNameLabel(conn);
        final String isoURL = _configDriveIsopath + vmName + ".iso";
        VDI srVdi;

        //1. find the vdi of the iso
        //2. find the vbd for the vdi
        //3. attach iso to vm

        try {
            final Set<VDI> vdis = VDI.getByNameLabel(conn, vmName + ".iso");
            if (vdis.isEmpty()) {
                throw new CloudRuntimeException("Could not find ISO with URL: " + isoURL);
            }
            srVdi = vdis.iterator().next();

        } catch (final XenAPIException e) {
            s_logger.debug("Unable to get config drive iso: " + isoURL + " due to " + e.toString());
            return false;
        } catch (final Exception e) {
            s_logger.debug("Unable to get config drive iso: " + isoURL + " due to " + e.toString());
            return false;
        }

        VBD isoVBD = null;

        // Find the VM's CD-ROM VBD
        final Set<VBD> vbds = vm.getVBDs(conn);
        for (final VBD vbd : vbds) {
            final Types.VbdType type = vbd.getType(conn);

            final VBD.Record vbdr = vbd.getRecord(conn);

            // if the device exists then attach it
            if (!vbdr.userdevice.equals(_attachIsoDeviceNum) && type == Types.VbdType.CD) {
                isoVBD = vbd;
                break;
            }
        }

        if (isoVBD == null) {
            //create vbd
            final VBD.Record cfgDriveVbdr = new VBD.Record();
            cfgDriveVbdr.VM = vm;
            cfgDriveVbdr.empty = true;
            cfgDriveVbdr.bootable = false;
            cfgDriveVbdr.userdevice = "autodetect";
            cfgDriveVbdr.mode = Types.VbdMode.RO;
            cfgDriveVbdr.type = Types.VbdType.CD;
            final VBD cfgDriveVBD = VBD.create(conn, cfgDriveVbdr);
            isoVBD = cfgDriveVBD;

            s_logger.debug("Created CD-ROM VBD for VM: " + vm);
        }

        if (isoVBD != null) {
            // If an ISO is already inserted, eject it
            if (isoVBD.getEmpty(conn) == false) {
                isoVBD.eject(conn);
            }

            try {
                // Insert the new ISO
                isoVBD.insert(conn, srVdi);
                s_logger.debug("Attached config drive iso to vm " + vmName);
            } catch (final XmlRpcException ex) {
                s_logger.debug("Failed to attach config drive iso to vm " + vmName);
                return false;
            }
        }

        return true;
    }

};