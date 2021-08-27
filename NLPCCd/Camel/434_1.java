//,temp,sample_1939.java,2,12,temp,sample_3164.java,2,10
//,3
public class xxx {
protected void doStop() throws Exception {
super.doStop();
if (additionalApplicationContext != null) {
IOHelper.close(additionalApplicationContext);
}
if (applicationContext != null) {


log.info("stopping spring applicationcontext");
}
}

};