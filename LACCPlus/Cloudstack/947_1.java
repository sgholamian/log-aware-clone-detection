//,temp,Ovm3HypervisorNetwork.java,171,186,temp,OvmResourceBase.java,888,901
//,3
public class xxx {
    public Answer execute(PingTestCommand cmd) {
        try {
            if (cmd.getComputingHostIp() != null) {
                CloudstackPlugin cSp = new CloudstackPlugin(c);
                if (!cSp.ping(cmd.getComputingHostIp())) {
                    return new Answer(cmd, false, "ping failed");
                }
            } else {
                return new Answer(cmd, false, "why asks me to ping a router???");
            }
            return new Answer(cmd, true, "success");
        } catch (Ovm3ResourceException e) {
            LOGGER.debug("Ping " + cmd.getComputingHostIp() + " failed", e);
            return new Answer(cmd, false, e.getMessage());
        }
    }

};