//,temp,sample_2974.java,2,14,temp,sample_2975.java,2,16
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