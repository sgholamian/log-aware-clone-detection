//,temp,UsageAlertManagerImpl.java,176,231,temp,AlertManagerImpl.java,746,806
//,3
public class xxx {
        public void sendAlert(AlertType alertType, long dataCenterId, Long podId, Long clusterId, String subject, String content) throws MessagingException,
        UnsupportedEncodingException {
            s_logger.warn("AlertType:: " + alertType + " | dataCenterId:: " + dataCenterId + " | podId:: " +
                    podId + " | clusterId:: " + clusterId + " | message:: " + subject);
            AlertVO alert = null;
            if ((alertType != AlertManager.AlertType.ALERT_TYPE_HOST) &&
                    (alertType != AlertManager.AlertType.ALERT_TYPE_USERVM) &&
                    (alertType != AlertManager.AlertType.ALERT_TYPE_DOMAIN_ROUTER) &&
                    (alertType != AlertManager.AlertType.ALERT_TYPE_CONSOLE_PROXY) &&
                    (alertType != AlertManager.AlertType.ALERT_TYPE_SSVM) &&
                    (alertType != AlertManager.AlertType.ALERT_TYPE_STORAGE_MISC) &&
                    (alertType != AlertManager.AlertType.ALERT_TYPE_MANAGMENT_NODE) &&
                    (alertType != AlertManager.AlertType.ALERT_TYPE_RESOURCE_LIMIT_EXCEEDED) &&
                    (alertType != AlertManager.AlertType.ALERT_TYPE_UPLOAD_FAILED) &&
                    (alertType != AlertManager.AlertType.ALERT_TYPE_OOBM_AUTH_ERROR) &&
                    (alertType != AlertManager.AlertType.ALERT_TYPE_HA_ACTION) &&
                    (alertType != AlertManager.AlertType.ALERT_TYPE_CA_CERT)) {
                alert = _alertDao.getLastAlert(alertType.getType(), dataCenterId, podId, clusterId);
            }

            if (alert == null) {
                // set up a new alert
                AlertVO newAlert = new AlertVO();
                newAlert.setType(alertType.getType());
                newAlert.setSubject(subject);
                newAlert.setContent(content);
                newAlert.setClusterId(clusterId);
                newAlert.setPodId(podId);
                newAlert.setDataCenterId(dataCenterId);
                newAlert.setSentCount(1); // Initialize sent count to 1 since we are now sending an alert.
                newAlert.setLastSent(new Date());
                newAlert.setName(alertType.getName());
                _alertDao.persist(newAlert);
            } else {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Have already sent: " + alert.getSentCount() + " emails for alert type '" + alertType + "' -- skipping send email");
                }
                return;
            }

            if (_smtpSession != null) {
                SMTPMessage msg = new SMTPMessage(_smtpSession);
                msg.setSender(new InternetAddress(_emailSender, _emailSender));
                msg.setFrom(new InternetAddress(_emailSender, _emailSender));
                for (InternetAddress address : _recipientList) {
                    msg.addRecipient(RecipientType.TO, address);
                }
                msg.setSubject(subject);
                msg.setSentDate(new Date());
                msg.setContent(content, "text/plain");
                msg.saveChanges();

                SMTPTransport smtpTrans = null;
                if (_smtpUseAuth) {
                    smtpTrans = new SMTPSSLTransport(_smtpSession, new URLName("smtp", _smtpHost, _smtpPort, null, _smtpUsername, _smtpPassword));
                } else {
                    smtpTrans = new SMTPTransport(_smtpSession, new URLName("smtp", _smtpHost, _smtpPort, null, _smtpUsername, _smtpPassword));
                }
                sendMessage(smtpTrans, msg);
            }
        }

};