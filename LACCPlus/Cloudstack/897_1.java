//,temp,Ovm3HypervisorSupport.java,554,571,temp,Ovm3HypervisorSupport.java,331,341
//,3
public class xxx {
    public CheckHealthAnswer execute(CheckHealthCommand cmd) {
        Common test = new Common(c);
        String ping = "put";
        String pong;
        try {
            pong = test.echo(ping);
        } catch (Ovm3ResourceException e) {
            LOGGER.debug("CheckHealth went wrong: " + config.getAgentHostname()
                    + ", " + e.getMessage(), e);
            return new CheckHealthAnswer(cmd, false);
        }
        if (ping.contentEquals(pong)) {
            return new CheckHealthAnswer(cmd, true);
        }
        LOGGER.debug("CheckHealth did not receive " + ping + " but got " + pong
                + " from " + config.getAgentHostname());
        return new CheckHealthAnswer(cmd, false);
    }

};