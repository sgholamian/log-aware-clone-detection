//,temp,UsageAlertManagerImpl.java,97,111,temp,AlertManagerImpl.java,238,256
//,3
public class xxx {
    @Override
    public void sendAlert(AlertType alertType, long dataCenterId, Long podId, String subject, String body) {
        // TODO:  queue up these messages and send them as one set of issues once a certain number of issues is reached?  If that's the case,
        //         shouldn't we have a type/severity as part of the API so that severe errors get sent right away?
        try {
            if (_emailAlert != null) {
                _emailAlert.sendAlert(alertType, dataCenterId, podId, subject, body);
            } else {
                s_alertsLogger.warn(" alertType:: " + alertType + " // dataCenterId:: " + dataCenterId + " // podId:: " + podId + " // clusterId:: " + null +
                    " // message:: " + subject + " // body:: " + body);
            }
        } catch (Exception ex) {
            s_logger.error("Problem sending email alert", ex);
        }
    }

};