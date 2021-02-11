//,temp,QuotaAlertManagerImpl.java,384,418,temp,ProjectManagerImpl.java,966,1000
//,3
public class xxx {
        public void sendQuotaAlert(List<String> emails, String subject, String body) throws MessagingException, UnsupportedEncodingException {
            if (_smtpSession == null) {
                s_logger.error("Unable to create smtp session.");
                return;
            }
            SMTPMessage msg = new SMTPMessage(_smtpSession);
            msg.setSender(new InternetAddress(_emailSender, _emailSender));
            msg.setFrom(new InternetAddress(_emailSender, _emailSender));

            for (String email : emails) {
                if (email != null && !email.isEmpty()) {
                    try {
                        InternetAddress address = new InternetAddress(email, email);
                        msg.addRecipient(Message.RecipientType.TO, address);
                    } catch (Exception pokemon) {
                        s_logger.error("Exception in creating address for:" + email, pokemon);
                    }
                }
            }

            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setContent(body, "text/html; charset=utf-8");
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

};