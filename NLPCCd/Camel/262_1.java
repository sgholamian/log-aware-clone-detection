//,temp,sample_1931.java,2,9,temp,sample_5510.java,2,10
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
if (!(exchange.getIn().getBody() instanceof javax.mail.Message)) {
SendEmailRequest request = createMailRequest(exchange);


log.info("sending request from exchange");
}
}

};