//,temp,sample_1080.java,6,17,temp,sample_1076.java,2,11
//,3
public class xxx {
private void closeOffsetReader() {
if (offsetReader == null) return;
try {
offsetReader.close();
} catch (Exception ex) {


log.info("failed to close source reader");
}
}

};