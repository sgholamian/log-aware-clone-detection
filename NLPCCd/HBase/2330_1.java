//,temp,sample_1505.java,2,13,temp,sample_54.java,2,15
//,3
public class xxx {
protected void abortWriter(T writer) throws IOException {
FileSystem fs = store.getFileSystem();
for (Path leftoverFile : writer.abortWriters()) {
try {
fs.delete(leftoverFile, false);
} catch (IOException e) {


log.info("failed to delete the leftover file after an unfinished compaction");
}
}
}

};