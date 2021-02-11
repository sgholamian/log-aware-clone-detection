//,temp,XenServer56FenceCommandWrapper.java,42,72,temp,XenServer56FP1FenceCommandWrapper.java,46,93
//,3
public class xxx {
    @Override
    public Answer execute(final FenceCommand command, final XenServer56Resource xenServer56) {
        final Connection conn = xenServer56.getConnection();
        try {
            final Boolean alive = xenServer56.checkHeartbeat(command.getHostGuid());
            if (alive == null) {
                s_logger.debug("Failed to check heartbeat,  so unable to fence");
                return new FenceAnswer(command, false, "Failed to check heartbeat, so unable to fence");
            }
            if (alive) {
                s_logger.debug("Heart beat is still going so unable to fence");
                return new FenceAnswer(command, false, "Heartbeat is still going on unable to fence");
            }
            final Set<VM> vms = VM.getByNameLabel(conn, command.getVmName());
            for (final VM vm : vms) {
                s_logger.info("Fence command for VM " + command.getVmName());
                vm.powerStateReset(conn);
                vm.destroy(conn);
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