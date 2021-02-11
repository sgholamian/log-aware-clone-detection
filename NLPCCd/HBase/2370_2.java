//,temp,sample_3023.java,2,12,temp,sample_3278.java,2,14
//,3
public class xxx {
public void run() {
try {
doRunLoop();
} finally {
try {
writeSelector.close();
} catch (IOException ioe) {


log.info("couldn t close write selector");
}
}
}

};