//,temp,sample_3077.java,2,10,temp,sample_3076.java,2,10
//,2
public class xxx {
public void setOutputFormatMockInstance(JobConf conf, String instanceName) {
try {
AccumuloOutputFormat.setMockInstance(conf, instanceName);
} catch (IllegalStateException e) {


log.info("ignoring exception setting mock instance of");
}
}

};