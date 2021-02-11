//,temp,sample_1107.java,2,17,temp,sample_8708.java,2,17
//,3
public class xxx {
public void dummy_method(){
NativeAzureFileSystem fs = getFileSystem();
FileStatus status = fs.getFileStatus(hugefile);
long filesize = status.getLen();
long blocks = filesize / UPLOAD_BLOCKSIZE;
byte[] data = new byte[UPLOAD_BLOCKSIZE];
ContractTestUtils.NanoTimer timer = new ContractTestUtils.NanoTimer();
try (FSDataInputStream in = openDataFile()) {
for (long block = 0; block < blocks; block++) {
in.readFully(data);
}


log.info("final stream state");
}
}

};