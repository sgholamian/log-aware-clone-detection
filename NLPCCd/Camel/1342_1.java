//,temp,sample_5972.java,2,19,temp,sample_5975.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (splitURI[1] != null) {
scheme = splitURI[0];
Component component = getComponent(scheme);
if (component != null) {
if (component.useRawUri()) {
answer = component.createEndpoint(rawUri);
} else {
answer = component.createEndpoint(uri);
}
if (answer != null && log.isDebugEnabled()) {


log.info("converted to endpoint by component");
}
}
}
}

};