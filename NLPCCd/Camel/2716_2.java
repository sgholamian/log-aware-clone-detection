//,temp,sample_7021.java,2,12,temp,sample_6092.java,2,13
//,3
public class xxx {
private void internalBeforeStop() {
try {
if (camelTemplate != null) {
ServiceHelper.stopService(camelTemplate);
camelTemplate = null;
}
} catch (Exception e) {


log.info("error stopping cameltemplate due this exception is ignored");
}
}

};