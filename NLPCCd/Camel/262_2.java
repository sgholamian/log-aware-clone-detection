//,temp,sample_1931.java,2,9,temp,sample_5510.java,2,10
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
Message in = exchange.getIn();
Object message = in.getBody();
if (message != null) {


log.info("sending out");
}
}

};