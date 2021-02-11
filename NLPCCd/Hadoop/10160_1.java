//,temp,sample_4381.java,2,13,temp,sample_9424.java,2,16
//,3
public class xxx {
public FolderRenamePending(Path redoFile, NativeAzureFileSystem fs) throws IllegalArgumentException, IOException {
this.fs = fs;
Path f = redoFile;
FSDataInputStream input = fs.open(f);
byte[] bytes = new byte[MAX_RENAME_PENDING_FILE_SIZE];
int l = input.read(bytes);
if (l <= 0) {


log.info("deleting empty rename pending file no data available");
}
}

};