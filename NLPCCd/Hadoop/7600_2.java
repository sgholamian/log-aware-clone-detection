//,temp,sample_1107.java,2,17,temp,sample_8708.java,2,17
//,3
public class xxx {
public void dummy_method(){
S3AFileSystem fs = getFileSystem();
FileStatus status = fs.getFileStatus(hugefile);
long filesize = status.getLen();
long blocks = filesize / uploadBlockSize;
byte[] data = new byte[uploadBlockSize];
ContractTestUtils.NanoTimer timer = new ContractTestUtils.NanoTimer();
try (FSDataInputStream in = fs.open(hugefile, uploadBlockSize)) {
for (long block = 0; block < blocks; block++) {
in.readFully(data);
}


log.info("final stream state");
}
}

};