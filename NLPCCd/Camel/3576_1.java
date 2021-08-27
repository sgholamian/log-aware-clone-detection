//,temp,sample_5265.java,2,18,temp,sample_3008.java,2,18
//,3
public class xxx {
public void dummy_method(){
for (Class<?> cl : models) {
Message message = cl.getAnnotation(Message.class);
Section section = cl.getAnnotation(Section.class);
if (message != null) {
ObjectHelper.notNull(message.pairSeparator(), "No Pair Separator has been defined in the @Message annotation");
pairSeparator = message.pairSeparator();
ObjectHelper.notNull(message.keyValuePairSeparator(), "No Key Value Pair Separator has been defined in the @Message annotation");
keyValuePairSeparator = message.keyValuePairSeparator();
crlf = message.crlf();
messageOrdered = message.isOrdered();


log.info("is the message ordered in output");
}
}
}

};