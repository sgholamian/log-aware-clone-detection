//,temp,sample_3128.java,2,9,temp,sample_1710.java,2,8
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
if (exchange.getIn().getBody() == null) {


log.info("received exchange with empty body skipping");
}
}

};