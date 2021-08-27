//,temp,sample_650.java,2,12,temp,sample_6390.java,2,12
//,3
public class xxx {
protected void doStop() throws Exception {
if (cs != null) {
try {
cs.stop();
} catch (IOException e) {


log.info("error occurred during stopping jmxconnectorservice this exception will be ignored");
}
}
}

};