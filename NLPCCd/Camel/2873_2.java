//,temp,sample_2707.java,2,13,temp,sample_2706.java,2,10
//,3
public class xxx {
public Bus getBus() {
if (bus == null) {
bus = CxfEndpointUtils.createBus(getCamelContext());
this.createBus = true;


log.info("using defaultbus");
}
}

};