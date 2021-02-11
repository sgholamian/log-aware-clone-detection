//,temp,UsageAlertManagerImpl.java,176,231,temp,AlertManagerImpl.java,746,806
//,3
public class xxx {
        protected void sendAlert(AlertType alertType, long dataCenterId, Long podId, String subject, String content) throws MessagingException,
            UnsupportedEncodingException {
            s_alertsLogger.warn(" alertType:: " + alertType + " // dataCenterId:: " + dataCenterId + " // podId:: " +
                podId + " // clusterId:: " + null + " // message:: " + subject);
            AlertVO alert = null;
            if ((alertType != AlertManager.AlertType.ALERT_TYPE_HOST) &&
                (alertType != AlertManager.AlertType.ALERT_TYPE_USERVM) &&
                (alertType != AlertManager.AlertType.ALERT_TYPE_DOMAIN_ROUTER) &&
                (alertType != AlertManager.AlertType.ALERT_TYPE_CONSOLE_PROXY) &&
                (alertType != AlertManager.AlertType.ALERT_TYPE_SSVM) &&
                (alertType != AlertManager.AlertType.ALERT_TYPE_STORAGE_MISC) &&
                (alertType != AlertManager.AlertType.ALERT_TYPE_MANAGMENT_NODE)) {
                alert = _alertDao.getLastAlert(alertType.getType(), dataCenterId, podId);
            }

            if (alert == null) {
                // set up a new alert
                AlertVO newAlert = new AlertVO();
                newAlert.setType(alertType.getType());
                newAlert.setSubject(subject);
                newAlert.setPodId(podId);
                newAlert.setDataCenterId(dataCenterId);
                newAlert.setSentCount(1); // initialize sent count to 1 since we are now sending an alert
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
                smtpTrans.connect();
                smtpTrans.sendMessage(msg, msg.getAllRecipients());
                smtpTrans.close();
            }
        }

};