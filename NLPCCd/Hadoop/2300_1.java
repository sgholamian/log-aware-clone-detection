//,temp,sample_8404.java,2,17,temp,sample_8403.java,2,16
//,3
public class xxx {
public void dummy_method(){
DFSInputStream in = dfs.dfs.open(corruptedFile.toUri().getPath());
byte[] buf = new byte[buffersize];
int startPosition = 2;
int nRead = 0;
try {
do {
nRead = in.read(startPosition, buf, 0, buf.length);
startPosition += buf.length;
} while (nRead > 0);
} catch (BlockMissingException bme) {


log.info("dfsclientreadfile caught blockmissingexception");
}
}

};