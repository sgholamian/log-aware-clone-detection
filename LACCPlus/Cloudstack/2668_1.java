//,temp,CitrixResourceBase.java,4380,4411,temp,CitrixPlugNicCommandWrapper.java,42,93
//,3
public class xxx {
    private ExecutionResult prepareNetworkElementCommand(final SetupGuestNetworkCommand cmd) {
        final Connection conn = getConnection();
        final NicTO nic = cmd.getNic();
        final String domrName = cmd.getAccessDetail(NetworkElementCommand.ROUTER_NAME);
        try {
            final Set<VM> vms = VM.getByNameLabel(conn, domrName);
            if (vms == null || vms.isEmpty()) {
                return new ExecutionResult(false, "Can not find VM " + domrName);
            }
            final VM vm = vms.iterator().next();
            final String mac = nic.getMac();
            VIF domrVif = null;
            for (final VIF vif : vm.getVIFs(conn)) {
                final String lmac = vif.getMAC(conn);
                if (lmac.equals(mac)) {
                    domrVif = vif;
                    // Do not break it! We have 2 routers.
                    // break;
                }
            }
            if (domrVif == null) {
                return new ExecutionResult(false, "Can not find vif with mac " + mac + " for VM " + domrName);
            }

            nic.setDeviceId(Integer.parseInt(domrVif.getDevice(conn)));
        } catch (final Exception e) {
            final String msg = "Creating guest network failed due to " + e.toString();
            s_logger.warn(msg, e);
            return new ExecutionResult(false, msg);
        }
        return new ExecutionResult(true, null);
    }

};