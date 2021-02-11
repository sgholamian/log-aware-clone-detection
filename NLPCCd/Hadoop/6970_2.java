//,temp,sample_3763.java,2,14,temp,sample_151.java,2,11
//,3
public class xxx {
protected void closeCurrentBlockReaders() {
if (blockReader == null) return;
try {
blockReader.close();
} catch (IOException e) {


log.info("error closing blockreader");
}
}

};