//,temp,ConsoleProxyMonitor.java,44,57,temp,LibvirtComputingResource.java,486,503
//,3
public class xxx {
    public ConsoleProxyMonitor(String[] argv) {
        _argv = argv;

        for (String arg : _argv) {
            String[] tokens = arg.split("=");
            if (tokens.length == 2) {
                s_logger.info("Add argument " + tokens[0] + "=" + tokens[1] + " to the argument map");

                _argMap.put(tokens[0].trim(), tokens[1].trim());
            } else {
                s_logger.warn("unrecognized argument, skip adding it to argument map");
            }
        }
    }

};