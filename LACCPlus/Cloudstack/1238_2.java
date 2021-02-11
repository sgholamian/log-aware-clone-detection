//,temp,XenServer56FenceCommandWrapper.java,42,72,temp,XenServer56FP1FenceCommandWrapper.java,46,93
//,3
public class xxx {
    @Override
    public Answer execute(final FenceCommand command, final XenServer56Resource xenServer56) {
        final Connection conn = xenServer56.getConnection();
        try {
            final Boolean alive = xenServer56.checkHeartbeat(command.getHostGuid());
            if ( alive == null ) {
                s_logger.debug("Failed to check heartbeat,  so unable to fence");
                return new FenceAnswer(command, false, "Failed to check heartbeat, so unable to fence");
            }
            if ( alive ) {
                s_logger.debug("Heart beat is still going so unable to fence");
                return new FenceAnswer(command, false, "Heartbeat is still going on unable to fence");
            }
            final Set<VM> vms = VM.getByNameLabel(conn, command.getVmName());
            for (final VM vm : vms) {
                final Set<VDI> vdis = new HashSet<VDI>();
                final Set<VBD> vbds = vm.getVBDs(conn);
                for (final VBD vbd : vbds) {
                    final VDI vdi = vbd.getVDI(conn);
                    if (!xenServer56.isRefNull(vdi)) {
                        vdis.add(vdi);
                    }
                }
                s_logger.info("Fence command for VM " + command.getVmName());
                vm.powerStateReset(conn);
                vm.destroy(conn);
                for (final VDI vdi : vdis) {
                    final Map<String, String> smConfig = vdi.getSmConfig(conn);
                    for (final String key : smConfig.keySet()) {
                        if (key.startsWith("host_")) {
                            vdi.removeFromSmConfig(conn, key);
                            break;
                        }
                    }
                }
            }
            return new FenceAnswer(command);
        } catch (final XmlRpcException e) {
            s_logger.warn("Unable to fence", e);
            return new FenceAnswer(command, false, e.getMessage());
        } catch (final XenAPIException e) {
            s_logger.warn("Unable to fence", e);
            return new FenceAnswer(command, false, e.getMessage());
        } catch (final Exception e) {
            s_logger.warn("Unable to fence", e);
            return new FenceAnswer(command, false, e.getMessage());
        }
    }

};