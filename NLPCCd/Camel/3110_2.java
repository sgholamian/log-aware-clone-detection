//,temp,sample_397.java,2,7,temp,sample_3666.java,2,9
//,3
public class xxx {
public void validate(Message message, DataType type) throws ValidationException {
Object body = message.getBody();
if (body instanceof String && body.equals(greeting)) {


log.info("OK");
}
}

};