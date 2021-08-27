//,temp,sample_2387.java,2,11,temp,sample_2388.java,2,12
//,3
public class xxx {
private boolean isValidMessage(String key, Message msg) {
boolean answer = true;
if (getEndpoint().getIdempotentRepository() != null) {
if (!getEndpoint().getIdempotentRepository().add(key)) {
answer = false;
}
}


log.info("message with key is valid");
}

};