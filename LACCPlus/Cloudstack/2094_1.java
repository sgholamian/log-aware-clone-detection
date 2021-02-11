//,temp,CitrixMigrateCommandWrapper.java,47,103,temp,CitrixAttachOrDettachConfigDriveCommandWrapper.java,44,94
//,3
public class xxx {
    @Override
    public Answer execute(final MigrateCommand command, final CitrixResourceBase citrixResourceBase) {
        final Connection conn = citrixResourceBase.getConnection();
        final String vmName = command.getVmName();
        final String dstHostIpAddr = command.getDestinationIp();

        try {
            final Set<VM> vms = VM.getByNameLabel(conn, vmName);

            final Set<Host> hosts = Host.getAll(conn);
            Host dsthost = null;
            if(hosts != null) {
                for (final Host host : hosts) {
                    if (host.getAddress(conn).equals(dstHostIpAddr)) {
                        dsthost = host;
                        break;
                    }
                }
            }
            if (dsthost == null) {
                final String msg = "Migration failed due to unable to find host " + dstHostIpAddr + " in XenServer pool " + citrixResourceBase.getHost().getPool();
                s_logger.warn(msg);
                return new MigrateAnswer(command, false, msg, null);
            }
            for (final VM vm : vms) {
                final Set<VBD> vbds = vm.getVBDs(conn);
                for (final VBD vbd : vbds) {
                    final VBD.Record vbdRec = vbd.getRecord(conn);
                    if (vbdRec.type.equals(Types.VbdType.CD) && !vbdRec.empty) {
                        vbd.eject(conn);
                        // for config drive vbd destroy the vbd.
                        if (!vbdRec.userdevice.equals(citrixResourceBase._attachIsoDeviceNum)) {
                            if (vbdRec.currentlyAttached) {
                                vbd.destroy(conn);
                            }
                        }
                        continue;
                    }
                }
                citrixResourceBase.migrateVM(conn, dsthost, vm, vmName);
                vm.setAffinity(conn, dsthost);

                destroyMigratedVmNetworkRulesOnSourceHost(command, citrixResourceBase, conn, dsthost);
            }

            // The iso can be attached to vm only once the vm is (present in the host) migrated.
            // Attach the config drive iso device to VM
            if (!citrixResourceBase.attachConfigDriveToMigratedVm(conn, vmName, dstHostIpAddr)) {
                s_logger.debug("Config drive ISO attach failed after migration for vm "+vmName);
            }

            return new MigrateAnswer(command, true, "migration succeeded", null);
        } catch (final Exception e) {
            s_logger.warn(e.getMessage(), e);
            return new MigrateAnswer(command, false, e.getMessage(), null);
        }
    }

};