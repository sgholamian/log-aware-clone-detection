//,temp,sample_5263.java,2,18,temp,sample_5264.java,2,19
//,3
public class xxx {
private void initMessageParameters() {
if ((pairSeparator == null) || (keyValuePairSeparator == null)) {
for (Class<?> cl : models) {
Message message = cl.getAnnotation(Message.class);
Section section = cl.getAnnotation(Section.class);
if (message != null) {
ObjectHelper.notNull(message.pairSeparator(), "No Pair Separator has been defined in the @Message annotation");
pairSeparator = message.pairSeparator();
ObjectHelper.notNull(message.keyValuePairSeparator(), "No Key Value Pair Separator has been defined in the @Message annotation");
keyValuePairSeparator = message.keyValuePairSeparator();


log.info("key value pair separator defined for the message");
}
}
}
}

};