//,temp,sample_3738.java,2,10,temp,sample_3737.java,2,10
//,2
public class xxx {
public void tearDown() throws Exception {
super.tearDown();
DefaultCamelContext dcc = (DefaultCamelContext)context;
while (!dcc.isStopped()) {


log.info("waiting on the camel context to stop");
}
}

};