//,temp,sample_2496.java,2,14,temp,sample_4048.java,2,14
//,3
public class xxx {
public void run() {
try {
doRunLoop();
} finally {
try {
readSelector.close();
} catch (IOException ioe) {


log.info("error closing read selector in");
}
}
}

};