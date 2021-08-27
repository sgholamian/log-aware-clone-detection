//,temp,sample_62.java,2,13,temp,sample_4010.java,2,14
//,3
public class xxx {
public void close() {
isOpen = false;
if (hiveSparkClient != null) {
try {
hiveSparkClient.close();
cleanScratchDir();
} catch (IOException e) {


log.info("failed to close spark session");
}
}
}

};