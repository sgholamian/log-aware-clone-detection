//,temp,sample_8112.java,2,12,temp,sample_6634.java,2,14
//,3
public class xxx {
public void transform(Message message, DataType from, DataType to) throws Exception {
Exchange exchange = message.getExchange();
CamelContext context = exchange.getContext();
if (from.isJavaType()) {
Object input = message.getBody();
Class<?> fromClass = context.getClassResolver().resolveClass(from.getName());
if (!fromClass.isAssignableFrom(input.getClass())) {


log.info("converting to");
}
}
}

};