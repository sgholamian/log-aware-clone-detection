//,temp,sample_2717.java,2,14,temp,sample_3740.java,2,16
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