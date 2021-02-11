//,temp,sample_98.java,2,11,temp,sample_3090.java,2,11
//,3
public class xxx {
public void close() {
if (stream == null) return;
try {
stream.close();
} catch (IOException e) {


log.info("unable to close the wal file");
}
}

};