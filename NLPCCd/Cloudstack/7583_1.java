//,temp,sample_5716.java,2,17,temp,sample_4197.java,2,17
//,2
public class xxx {
public void dummy_method(){
EndPoint ep = _epSelector.select(store);
Answer answer = null;
if (ep == null) {
String errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
s_logger.error(errMsg);
answer = new Answer(dtCommand, false, errMsg);
} else {
answer = ep.sendMessage(dtCommand);
}
if (answer == null || !answer.getResult()) {


log.info("failed to deleted volume at store");
}
}

};