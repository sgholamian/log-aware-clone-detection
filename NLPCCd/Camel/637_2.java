//,temp,sample_5934.java,2,10,temp,sample_957.java,2,8
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
Message in = exchange.getIn();
Pod pod = exchange.getIn().getBody(Pod.class);


log.info("got event with pod name and action");
}

};