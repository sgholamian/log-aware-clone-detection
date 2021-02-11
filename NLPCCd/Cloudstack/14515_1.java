//,temp,sample_7624.java,2,12,temp,sample_2657.java,2,12
//,2
public class xxx {
public void clearAlert(AlertType alertType, long dataCenterId, long podId) {
try {
if (_emailAlert != null) {
_emailAlert.clearAlert(alertType.getType(), dataCenterId, podId);
}
} catch (Exception ex) {


log.info("problem clearing email alert");
}
}

};