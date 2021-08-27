//,temp,sample_8473.java,2,8,temp,sample_5944.java,2,10
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
Message in = exchange.getIn();
Node node = exchange.getIn().getBody(Node.class);


log.info("got event with node name and action");
}

};