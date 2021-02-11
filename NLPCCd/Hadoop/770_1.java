//,temp,sample_7756.java,2,13,temp,sample_9007.java,2,18
//,3
public class xxx {
private void closeResponder() {
if (response != null) {
try {
response.close();
response.join();
} catch (InterruptedException  e) {


log.info("caught exception");
}
}
}

};