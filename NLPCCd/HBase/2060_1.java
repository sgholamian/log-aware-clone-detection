//,temp,sample_1295.java,2,13,temp,sample_1367.java,2,13
//,3
public class xxx {
public void close() {
if (this.scanner != null) {
this.scanner.close();
}
try {
this.htable.close();
} catch (IOException ioe) {


log.info("error closing table");
}
}

};