//,temp,sample_1302.java,2,8,temp,sample_801.java,2,8
//,3
public class xxx {
public List<AtmosphereRequest> onMessage(WebSocket webSocket, String data) {
String connectionKey = store.getConnectionKey(webSocket);
consumer.sendMessage(connectionKey, data);


log.info("text message sent");
}

};