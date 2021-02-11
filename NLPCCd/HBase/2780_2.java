//,temp,sample_53.java,2,11,temp,sample_54.java,2,15
//,3
public class xxx {
protected void abortWriter(StoreFileWriter writer) throws IOException {
Path leftoverFile = writer.getPath();
try {
writer.close();
} catch (IOException e) {
}
try {
store.getFileSystem().delete(leftoverFile, false);
} catch (IOException e) {


log.info("failed to delete the leftover file after an unfinished compaction");
}
}

};