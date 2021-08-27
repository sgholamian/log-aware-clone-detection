//,temp,sample_1686.java,2,16,temp,sample_1685.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (type != null) {
if (LOG.isDebugEnabled()) {
}
if (HeadersMapFactory.class.isAssignableFrom(type)) {
HeadersMapFactory answer = (HeadersMapFactory) context.getInjector().newInstance(type);
return answer;
} else {
throw new IllegalArgumentException("Type is not a HeadersMapFactory implementation. Found: " + type.getName());
}
}


log.info("creating default headersmapfactory");
}

};