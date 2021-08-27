//,temp,sample_1009.java,2,14,temp,sample_2512.java,2,12
//,3
public class xxx {
public boolean unregister() {
if (--registrationCount <= 0) {
try {
dataStore.close();
} catch (IOException e) {


log.info("error while closing datastore this exception is ignored");
}
}
}

};