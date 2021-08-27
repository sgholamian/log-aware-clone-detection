//,temp,sample_6067.java,2,8,temp,sample_4217.java,2,11
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
Message in = exchange.getIn();
Comment comment = (Comment) in.getBody();


log.info("got comment with id body");
}

};