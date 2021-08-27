//,temp,sample_8314.java,2,19,temp,sample_1543.java,2,19
//,2
public class xxx {
public void dummy_method(){
if (value instanceof String) {
String text = (String) value;
text = camelContext.resolvePropertyPlaceholders(text);
if (text != value) {
boolean changed = IntrospectionSupport.setProperty(camelContext.getTypeConverter(), definition, name, text);
if (!changed) {
throw new IllegalArgumentException("No setter to set property: " + name + " to: " + text + " on: " + definition);
}
changedProperties.put(name, value);
if (LOG.isDebugEnabled()) {


log.info("changed property from to");
}
}
}
}

};