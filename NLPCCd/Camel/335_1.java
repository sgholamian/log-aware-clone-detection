//,temp,sample_4247.java,2,8,temp,sample_6492.java,2,7
//,3
public class xxx {
public void validate(Message message, DataType type) throws ValidationException {
message.getExchange().setProperty(VALIDATOR_INVOKED, OtherXOrderValidator.class);
assertEquals("name=XOrder", message.getBody());


log.info("java validation other xorder");
}

};