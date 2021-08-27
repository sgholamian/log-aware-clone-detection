//,temp,sample_2718.java,2,16,temp,sample_3739.java,2,14
//,3
public class xxx {
public void dummy_method(){
while (!dcc.isStopped()) {
}
if (getSession() != null) {
getSession().close();
setSession(null);
}
if (connection != null) {
connection.stop();
connection = null;
}


log.info("stopping the activemq broker");
}

};