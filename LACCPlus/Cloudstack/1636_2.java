//,temp,VirtualRoutingResource.java,189,204,temp,ConsoleProxyResource.java,97,113
//,3
public class xxx {
    @Override
    public Answer executeRequest(final Command cmd) {
        if (cmd instanceof CheckConsoleProxyLoadCommand) {
            return execute((CheckConsoleProxyLoadCommand)cmd);
        } else if (cmd instanceof WatchConsoleProxyLoadCommand) {
            return execute((WatchConsoleProxyLoadCommand)cmd);
        } else if (cmd instanceof ReadyCommand) {
            s_logger.info("Receive ReadyCommand, response with ReadyAnswer");
            return new ReadyAnswer((ReadyCommand)cmd);
        } else if (cmd instanceof CheckHealthCommand) {
            return new CheckHealthAnswer((CheckHealthCommand)cmd, true);
        } else if (cmd instanceof StartConsoleProxyAgentHttpHandlerCommand) {
            return execute((StartConsoleProxyAgentHttpHandlerCommand)cmd);
        } else {
            return Answer.createUnsupportedCommandAnswer(cmd);
        }
    }

};