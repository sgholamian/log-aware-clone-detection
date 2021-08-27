//,temp,sample_7021.java,2,12,temp,sample_6092.java,2,13
//,3
public class xxx {
public void onCompleted() {
if (producer != null) {
try {
ServiceHelper.stopService(producer);
} catch (Exception e) {


log.info("error stopping producer due this exception is ignored");
}
}
}

};