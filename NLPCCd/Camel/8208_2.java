//,temp,sample_4333.java,2,10,temp,sample_4334.java,2,10
//,2
public class xxx {
public void removeWebSocket(WebSocket websocket) {
Object obj = keys.remove(websocket);
if (obj != null) {
values.remove(obj);
}


log.info("removed websocket");
}

};