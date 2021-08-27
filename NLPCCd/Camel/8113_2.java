//,temp,sample_3233.java,2,17,temp,sample_3234.java,2,17
//,3
public class xxx {
public void dummy_method(){
UpdateResult updateResult = service.getUpdates(config.getAuthorizationToken(), offset, config.getLimit(), config.getTimeout());
if (updateResult.getUpdates() == null) {
updateResult.setUpdates(Collections.emptyList());
}
if (!updateResult.isOk()) {
throw new IllegalStateException("The server was unable to process the request. Response was " + updateResult);
}
List<Update> updates = updateResult.getUpdates();
if (updates.size() > 0) {
} else {


log.info("no updates received from telegram service");
}
}

};