//,temp,sample_7520.java,2,11,temp,sample_6842.java,2,11
//,3
public class xxx {
public Object getProperty(String key) {
Object value = super.getProperty(key);
if (value == null) {
if (LOG.isDebugEnabled()) {


log.info("poking parent for key");
}
}
}

};