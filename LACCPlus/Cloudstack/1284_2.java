//,temp,QuotaAlertManagerImpl.java,384,418,temp,ProjectManagerImpl.java,966,1000
//,3
public class xxx {
        public void sendInvite(String token, String email, long projectId) throws MessagingException, UnsupportedEncodingException {
            if (_smtpSession != null) {
                InternetAddress address = null;
                if (email != null) {
                    try {
                        address = new InternetAddress(email, email);
                    } catch (Exception ex) {
                        s_logger.error("Exception creating address for: " + email, ex);
                    }
                }

                String content = "You've been invited to join the CloudStack project id=" + projectId + ". Please use token " + token + " to complete registration";

                SMTPMessage msg = new SMTPMessage(_smtpSession);
                msg.setSender(new InternetAddress(_emailSender, _emailSender));
                msg.setFrom(new InternetAddress(_emailSender, _emailSender));
                msg.addRecipient(RecipientType.TO, address);
                msg.setSubject("You are invited to join the cloud stack project id=" + projectId);
                msg.setSentDate(new Date(DateUtil.currentGMTTime().getTime() >> 10));
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
            } else {
                throw new CloudRuntimeException("Unable to send email invitation; smtp ses");
            }
        }

};