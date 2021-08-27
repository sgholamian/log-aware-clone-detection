//,temp,sample_4957.java,2,8,temp,sample_3089.java,2,7
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
Message in = exchange.getIn();
Comment comment = (Comment) in.getBody();


log.info("got comment from user");
}

};