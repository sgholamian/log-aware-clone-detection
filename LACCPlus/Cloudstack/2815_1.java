//,temp,CitrixResourceBase.java,5536,5604,temp,CitrixResourceBase.java,5373,5446
//,3
public class xxx {
    public boolean attachConfigDriveToMigratedVm(Connection conn, String vmName, String ipAddr) {

        // attach the config drive in destination host

        try {
            s_logger.debug("Attaching config drive iso device for the VM " + vmName + " In host " + ipAddr);
            Set<VM> vms = VM.getByNameLabel(conn, vmName);

            SR sr = getSRByNameLabel(conn, _configDriveSRName + ipAddr);
            //Here you will find only two vdis with the <vmname>.iso.
            //one is from source host and second from dest host
            Set<VDI> vdis = VDI.getByNameLabel(conn, vmName + ".iso");
            if (vdis.isEmpty()) {
                s_logger.debug("Could not find config drive ISO: " + vmName);
                return false;
            }

            VDI configdriveVdi = null;
            for (VDI vdi : vdis) {
                SR vdiSr = vdi.getSR(conn);
                if (vdiSr.getUuid(conn).equals(sr.getUuid(conn))) {
                    //get this vdi to attach to vbd
                    configdriveVdi = vdi;
                    s_logger.debug("VDI for the config drive ISO  " + vdi);
                } else {
                    // delete the vdi in source host so that the <vmname>.iso file is get removed
                    s_logger.debug("Removing the source host VDI for the config drive ISO  " + vdi);
                    vdi.destroy(conn);
                }
            }

            if (configdriveVdi == null) {
                s_logger.debug("Config drive ISO VDI is not found ");
                return false;
            }

            for (VM vm : vms) {

                //create vbd
                VBD.Record cfgDriveVbdr = new VBD.Record();
                cfgDriveVbdr.VM = vm;
                cfgDriveVbdr.empty = true;
                cfgDriveVbdr.bootable = false;
                cfgDriveVbdr.userdevice = "autodetect";
                cfgDriveVbdr.mode = Types.VbdMode.RO;
                cfgDriveVbdr.type = Types.VbdType.CD;

                VBD cfgDriveVBD = VBD.create(conn, cfgDriveVbdr);

                s_logger.debug("Inserting vbd " + configdriveVdi);
                cfgDriveVBD.insert(conn, configdriveVdi);
                break;

            }

            return true;

        } catch (BadServerResponse e) {
            s_logger.warn("Failed to attach config drive ISO to the VM  " + vmName + " In host " + ipAddr + " due to a bad server response.", e);
            return false;
        } catch (XenAPIException e) {
            s_logger.warn("Failed to attach config drive ISO to the VM  " + vmName + " In host " + ipAddr + " due to a xapi problem.", e);
            return false;
        } catch (XmlRpcException e) {
            s_logger.warn("Failed to attach config drive ISO to the VM  " + vmName + " In host " + ipAddr + " due to a problem in a remote call.", e);
            return false;
        }

    }

};