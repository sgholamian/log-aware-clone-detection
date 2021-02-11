//,temp,Ovm3HypervisorNetwork.java,171,186,temp,OvmResourceBase.java,888,901
//,3
public class xxx {
    protected Answer execute(PingTestCommand cmd) {
        try {
            if (cmd.getComputingHostIp() != null) {
                OvmHost.pingAnotherHost(_conn, cmd.getComputingHostIp());
            } else {
                return new Answer(cmd, false, "why asks me to ping router???");
            }

            return new Answer(cmd, true, "success");
        } catch (Exception e) {
            s_logger.debug("Ping " + cmd.getComputingHostIp() + " failed", e);
            return new Answer(cmd, false, e.getMessage());
        }
    }

};