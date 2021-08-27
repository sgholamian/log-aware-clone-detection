//,temp,sample_2708.java,2,14,temp,sample_2709.java,2,18
//,3
public class xxx {
public void setCamelContext(CamelContext c) {
super.setCamelContext(c);
if (this.properties != null) {
try {
EndpointHelper.setReferenceProperties(getCamelContext(), this, this.properties);
EndpointHelper.setProperties(getCamelContext(), this, this.properties);
} catch (Throwable e) {


log.info("error setting camelcontext this exception will be ignored");
}
}
}

};