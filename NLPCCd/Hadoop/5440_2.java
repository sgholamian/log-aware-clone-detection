//,temp,sample_3459.java,2,13,temp,sample_7756.java,2,13
//,2
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