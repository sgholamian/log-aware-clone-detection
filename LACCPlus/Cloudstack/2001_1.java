//,temp,SshKeysDistriMonitor.java,80,99,temp,AgentManagerImpl.java,1747,1764
//,3
public class xxx {
    @Override
    public void processConnect(Host host, StartupCommand cmd, boolean forRebalance) throws ConnectionException {
        if (cmd instanceof StartupRoutingCommand) {
            if (((StartupRoutingCommand)cmd).getHypervisorType() == HypervisorType.KVM || ((StartupRoutingCommand)cmd).getHypervisorType() == HypervisorType.XenServer ||
                ((StartupRoutingCommand)cmd).getHypervisorType() == HypervisorType.LXC) {
                /*TODO: Get the private/public keys here*/

                String pubKey = _configDao.getValue("ssh.publickey");
                String prvKey = _configDao.getValue("ssh.privatekey");

                try {
                    ModifySshKeysCommand cmds = new ModifySshKeysCommand(pubKey, prvKey);
                    Commands c = new Commands(cmds);
                    _agentMgr.send(host.getId(), c, this);
                } catch (AgentUnavailableException e) {
                    s_logger.debug("Failed to send keys to agent: " + host.getId());
                }
            }
        }
    }

};