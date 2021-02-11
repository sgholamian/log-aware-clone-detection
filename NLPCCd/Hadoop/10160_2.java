//,temp,sample_4381.java,2,13,temp,sample_9424.java,2,16
//,3
public class xxx {
private long tailFile(Path file, long startPos) throws IOException {
long numRead = 0;
FSDataInputStream inputStream = fileSystem.open(file);
inputStream.seek(startPos);
int len = 4 * 1024;
byte[] buf = new byte[len];
int read;
while ((read = inputStream.read(buf)) > -1) {
if (!validateSequentialBytes(buf, (int) (startPos + numRead), read)) {


log.info("invalid bytes s");
}
}
}

};