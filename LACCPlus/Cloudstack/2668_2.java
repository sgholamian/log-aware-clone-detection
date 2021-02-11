//,temp,CitrixResourceBase.java,4380,4411,temp,CitrixPlugNicCommandWrapper.java,42,93
//,3
public class xxx {
    @Override
    public Answer execute(final PlugNicCommand command, final CitrixResourceBase citrixResourceBase) {
        final Connection conn = citrixResourceBase.getConnection();
        final String vmName = command.getVmName();
        try {
            final Set<VM> vms = VM.getByNameLabel(conn, vmName);
            if (vms == null || vms.isEmpty()) {
                return new PlugNicAnswer(command, false, "Can not find VM " + vmName);
            }
            final VM vm = vms.iterator().next();
            final NicTO nic = command.getNic();

            String mac = nic.getMac();
            final Set<VIF> routerVIFs = vm.getVIFs(conn);
            mac = mac.trim();

            int counter = 0;
            for (final VIF vif : routerVIFs) {
                final String lmac = vif.getMAC(conn);
                if (lmac.trim().equals(mac)) {
                    counter++;
                }
            }
            // We allow 2 routers with the same mac. It's needed for the redundant vpc routers.
            // [FIXME] Find a way to identify the type of the router or if it's
            // redundant.
            if (counter > 2) {
                final String msg = " Plug Nic failed due to a VIF with the same mac " + nic.getMac() + " exists in more than 2 routers.";
                s_logger.error(msg);
                return new PlugNicAnswer(command, false, msg);
            }

            // Wilder Rodrigues - replaced this code with the code above.
            // VIF vif = getVifByMac(conn, vm, nic.getMac());
            // if (vif != null) {
            // final String msg = " Plug Nic failed due to a VIF with the same mac " + nic.getMac() + " exists";
            // s_logger.warn(msg);
            // return new PlugNicAnswer(cmd, false, msg);
            // }

            final String deviceId = citrixResourceBase.getLowestAvailableVIFDeviceNum(conn, vm);
            nic.setDeviceId(Integer.parseInt(deviceId));
            final VIF vif = citrixResourceBase.createVif(conn, vmName, vm, null, nic);
            // vif = createVif(conn, vmName, vm, null, nic);
            vif.plug(conn);
            return new PlugNicAnswer(command, true, "success");
        } catch (final Exception e) {
            final String msg = " Plug Nic failed due to " + e.toString();
            s_logger.error(msg, e);
            return new PlugNicAnswer(command, false, msg);
        }
    }

};