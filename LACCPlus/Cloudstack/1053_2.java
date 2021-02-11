//,temp,UsageAlertManagerImpl.java,251,260,temp,AlertManagerImpl.java,856,866
//,3
public class xxx {
    @Override
    @ActionEvent(eventType = EventTypes.ALERT_GENERATE, eventDescription = "generating alert", async = true)
    public boolean generateAlert(AlertType alertType, long dataCenterId, Long podId, String msg) {
        try {
            sendAlert(alertType, dataCenterId, podId, msg, msg);
            return true;
        } catch (Exception ex) {
            s_logger.warn("Failed to generate an alert of type=" + alertType + "; msg=" + msg);
            return false;
        }
    }

};