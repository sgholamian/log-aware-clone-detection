//,temp,sample_2708.java,2,14,temp,sample_2709.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (this.properties == null) {
this.properties = properties;
} else {
this.properties.putAll(properties);
}
if (getCamelContext() != null && this.properties != null) {
try {
EndpointHelper.setReferenceProperties(getCamelContext(), this, this.properties);
EndpointHelper.setProperties(getCamelContext(), this, this.properties);
} catch (Throwable e) {


log.info("error setting properties this exception will be ignored");
}
}
}

};