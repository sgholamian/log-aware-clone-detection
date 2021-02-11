//,temp,UsageAlertManagerImpl.java,86,95,temp,AlertManagerImpl.java,227,236
//,2
public class xxx {
    @Override
    public void clearAlert(AlertType alertType, long dataCenterId, long podId) {
        try {
            if (_emailAlert != null) {
                _emailAlert.clearAlert(alertType.getType(), dataCenterId, podId);
            }
        } catch (Exception ex) {
            s_logger.error("Problem clearing email alert", ex);
        }
    }

};