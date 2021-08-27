//,temp,sample_8112.java,2,12,temp,sample_6634.java,2,14
//,3
public class xxx {
private boolean convertIfRequired(Message message, DataType type) throws Exception {
if (type != null && type.isJavaType() && type.getName() != null) {
CamelContext context = message.getExchange().getContext();
Class<?> typeJava = getClazz(type.getName(), context);
if (!typeJava.isAssignableFrom(message.getBody().getClass())) {


log.info("converting to");
}
}
}

};