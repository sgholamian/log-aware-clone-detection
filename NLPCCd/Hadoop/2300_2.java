//,temp,sample_8404.java,2,17,temp,sample_8403.java,2,16
//,3
public class xxx {
private void dfsClientReadFile(Path corruptedFile) throws IOException, UnresolvedLinkException {
DFSInputStream in = dfs.dfs.open(corruptedFile.toUri().getPath());
byte[] buf = new byte[buffersize];
int nRead = 0;
try {
do {
nRead = in.read(buf, 0, buf.length);
} while (nRead > 0);
} catch (ChecksumException ce) {
} catch (BlockMissingException bme) {


log.info("dfsclientreadfile caught blockmissingexception");
}
}

};