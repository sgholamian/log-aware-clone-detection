//,temp,MllpEndpoint.java,311,328,temp,MllpEndpoint.java,292,309
//,3
public class xxx {
    public boolean checkAfterSendProperties(Exchange exchange, Socket socket, Logger log) {
        final String logMessageFormat = "Exchange property {} = {} - {} connection";
        boolean answer = true;

        if (exchange.getProperty(MllpConstants.MLLP_RESET_CONNECTION_AFTER_SEND, boolean.class)) {
            log.warn(logMessageFormat, MllpConstants.MLLP_RESET_CONNECTION_AFTER_SEND,
                    exchange.getProperty(MllpConstants.MLLP_RESET_CONNECTION_AFTER_SEND), "resetting");
            doConnectionClose(socket, true, log);
            answer = false;
        } else if (exchange.getProperty(MllpConstants.MLLP_CLOSE_CONNECTION_AFTER_SEND, boolean.class)) {
            log.warn(logMessageFormat, MllpConstants.MLLP_CLOSE_CONNECTION_AFTER_SEND,
                    exchange.getProperty(MllpConstants.MLLP_CLOSE_CONNECTION_AFTER_SEND), "closing");
            doConnectionClose(socket, false, log);
            answer = false;
        }

        return answer;
    }

};