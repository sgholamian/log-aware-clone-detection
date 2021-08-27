//,temp,sample_98.java,2,16,temp,sample_99.java,2,16
//,3
public class xxx {
public void dummy_method(){
final Set<ConstraintViolation<SMSMessage>> constraintViolations = getValidator().validate(smsMessage);
if (constraintViolations.size() > 0) {
final StringBuffer msg = new StringBuffer();
for (final ConstraintViolation<SMSMessage> cv : constraintViolations) {
msg.append(String.format("- Invalid value for %s: %s", cv.getPropertyPath().toString(), cv.getMessage()));
}
log.debug(msg.toString());
throw new InvalidPayloadRuntimeException(exchange, SMSMessage.class);
}
final CMMessage cmMessage = new CMMessage(smsMessage.getPhoneNumber(), smsMessage.getMessage());


log.info("cmmessage instance build from valid smsmessage instance");
}

};