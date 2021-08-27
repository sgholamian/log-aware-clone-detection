//,temp,sample_5934.java,2,10,temp,sample_957.java,2,8
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
Message in = exchange.getIn();
Object message = in.getBody();
if (message == null) {


log.info("ignoring a null message");
}
}

};