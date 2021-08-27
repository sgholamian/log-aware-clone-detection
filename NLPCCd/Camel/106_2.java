//,temp,sample_1007.java,2,14,temp,sample_6537.java,2,13
//,3
public class xxx {
protected void doStop() throws Exception {
super.doStop();
if (serviceDiscovery != null) {
try {
serviceDiscovery.close();
} catch (Exception e) {


log.info("error closing curator servicediscovery");
}
}
}

};