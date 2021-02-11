//,temp,SshKeysDistriMonitor.java,80,99,temp,AgentManagerImpl.java,1747,1764
//,3
public class xxx {
        @Override
        public void processConnect(final Host host, final StartupCommand cmd, final boolean forRebalance) {
            if (cmd instanceof StartupRoutingCommand) {
                if (((StartupRoutingCommand)cmd).getHypervisorType() == HypervisorType.KVM || ((StartupRoutingCommand)cmd).getHypervisorType() == HypervisorType.LXC) {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("router.aggregation.command.each.timeout", _configDao.getValue("router.aggregation.command.each.timeout"));

                    try {
                        SetHostParamsCommand cmds = new SetHostParamsCommand(params);
                        Commands c = new Commands(cmds);
                        send(host.getId(), c, this);
                    } catch (AgentUnavailableException e) {
                        s_logger.debug("Failed to send host params on host: " + host.getId());
                    }
                }
            }

        }

};