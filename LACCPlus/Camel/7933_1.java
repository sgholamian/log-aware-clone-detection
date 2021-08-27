//,temp,WsEndpoint.java,206,212,temp,WsEndpoint.java,198,204
//,3
public class xxx {
        @Override
        public void onTextFrame(String message, boolean finalFragment, int rsv) {
            LOG.debug("Received message --> {}", message);
            for (WsConsumer consumer : consumers) {
                consumer.sendMessage(message);
            }
        }

};