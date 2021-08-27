//,temp,sample_2718.java,2,16,temp,sample_3739.java,2,14
//,3
public class xxx {
public void tearDown() throws Exception {
super.tearDown();
DefaultCamelContext dcc = (DefaultCamelContext)context;
while (!dcc.isStopped()) {
}
if (getSession() != null) {
getSession().close();
setSession(null);
}


log.info("closing jms connection");
}

};