//,temp,sample_5722.java,2,11,temp,sample_4351.java,2,11
//,3
public class xxx {
public void after() {
kafkaServer.shutdown();
try {
TestUtils.deleteFile(logDir);
} catch (FileNotFoundException e) {


log.info("could not delete not found");
}
}

};