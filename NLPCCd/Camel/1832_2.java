//,temp,sample_6233.java,2,7,temp,sample_806.java,2,7
//,2
public class xxx {
public void process(Exchange exchange) throws Exception {
Exception ex = (Exception)exchange.getProperties().get(Exchange.EXCEPTION_CAUGHT);


log.info("attempting redelivery of handled exception with message");
}

};