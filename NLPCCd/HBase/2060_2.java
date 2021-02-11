//,temp,sample_1295.java,2,13,temp,sample_1367.java,2,13
//,3
public class xxx {
public void close() {
if (this.scanner != null) {
try {
this.scanner.close();
this.scanner = null;
} catch (IOException ex) {


log.info("exception while closing scanner");
}
}
}

};