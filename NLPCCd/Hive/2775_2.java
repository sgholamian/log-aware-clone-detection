//,temp,sample_5023.java,2,17,temp,sample_5021.java,2,16
//,3
public class xxx {
private int nextKeyValueTolerateCorruptions() throws IOException {
long currentOffset = in.getPos();
int ret;
try {
ret = nextKeyBuffer();
this.currentValueBuffer();
} catch (IOException ioe) {
String msg = ioe.getMessage();
if (msg != null && msg.startsWith(BLOCK_MISSING_MESSAGE)) {


log.info("re throwing block missing exception");
}
}
}

};