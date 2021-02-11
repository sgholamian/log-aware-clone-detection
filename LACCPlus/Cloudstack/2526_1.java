//,temp,ConsoleProxyManagerImpl.java,840,890,temp,ConsoleProxyManagerImpl.java,295,354
//,3
public class xxx {
    public void handleAgentDisconnect(long agentId, com.cloud.host.Status state) {
        if (state == com.cloud.host.Status.Alert || state == com.cloud.host.Status.Disconnected) {
            // be it either in alert or in disconnected state, the agent process
            // may be gone in the VM,
            // we will be reacting to stop the corresponding VM and let the scan
            // process to
            HostVO host = _hostDao.findById(agentId);
            if (host.getType() == Type.ConsoleProxy) {
                String name = host.getName();
                if (s_logger.isInfoEnabled()) {
                    s_logger.info("Console proxy agent disconnected, proxy: " + name);
                }
                if (name != null && name.startsWith("v-")) {
                    String[] tokens = name.split("-");
                    long proxyVmId = 0;
                    try {
                        proxyVmId = Long.parseLong(tokens[1]);
                    } catch (NumberFormatException e) {
                        s_logger.error("Unexpected exception " + e.getMessage(), e);
                        return;
                    }

                    final ConsoleProxyVO proxy = _consoleProxyDao.findById(proxyVmId);
                    if (proxy != null) {

                        // Disable this feature for now, as it conflicts with
                        // the case of allowing user to reboot console proxy
                        // when rebooting happens, we will receive disconnect
                        // here and we can't enter into stopping process,
                        // as when the rebooted one comes up, it will kick off a
                        // newly started one and trigger the process
                        // continue on forever

                        /*
                         * _capacityScanScheduler.execute(new Runnable() { public void run() {
                         * if(s_logger.isInfoEnabled())
                         * s_logger.info("Stop console proxy " + proxy.getName() +
                         * " VM because of that the agent running inside it has disconnected" );
                         * stopProxy(proxy.getId()); } });
                         */
                    } else {
                        if (s_logger.isInfoEnabled()) {
                            s_logger.info("Console proxy agent disconnected but corresponding console proxy VM no longer exists in DB, proxy: " + name);
                        }
                    }
                } else {
                    assert (false) : "Invalid console proxy name: " + name;
                }
            }
        }
    }

};