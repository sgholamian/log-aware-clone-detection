//,temp,sample_1933.java,2,14,temp,sample_1932.java,2,10
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
if (!(exchange.getIn().getBody() instanceof javax.mail.Message)) {
SendEmailRequest request = createMailRequest(exchange);
SendEmailResult result = getEndpoint().getSESClient().sendEmail(request);


log.info("received result");
}
}

};