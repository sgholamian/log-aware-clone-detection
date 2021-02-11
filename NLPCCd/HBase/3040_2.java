//,temp,sample_3007.java,2,18,temp,sample_4048.java,2,14
//,3
public class xxx {
public void shutdown() {
try {
fileChannel.close();
} catch (IOException ex) {
}
try {
raf.close();
} catch (IOException ex) {


log.info("can t shutdown cleanly");
}
}

};