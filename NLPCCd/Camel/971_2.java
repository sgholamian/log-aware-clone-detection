//,temp,sample_170.java,2,13,temp,sample_169.java,2,12
//,3
public class xxx {
private StatusLine sendRoomMessage(String room, Exchange exchange) throws IOException, InvalidPayloadException {
String urlPath = String.format(getConfig().withAuthToken(HipchatApiConstants.URI_PATH_ROOM_NOTIFY), room);
String backGroundColor = exchange.getIn().getHeader(HipchatConstants.MESSAGE_BACKGROUND_COLOR, String.class);
Map<String, String> jsonParam = getCommonHttpPostParam(exchange);
if (backGroundColor != null) {
jsonParam.put(HipchatApiConstants.API_MESSAGE_COLOR, backGroundColor);
}


log.info("sending message to room");
}

};