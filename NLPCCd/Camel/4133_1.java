//,temp,sample_6691.java,2,14,temp,sample_2176.java,2,9
//,3
public class xxx {
private static Object checkSerializableBody(String type, Exchange exchange, Object object) {
if (object == null) {
return null;
}
Serializable converted = exchange.getContext().getTypeConverter().convertTo(Serializable.class, exchange, object);
if (converted != null) {
return converted;
} else {


log.info("exchange containing object of type cannot be serialized it will be excluded by the holder");
}
}

};