//,temp,sample_2460.java,2,8,temp,sample_2129.java,2,8
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
Message in = exchange.getIn();
RepositoryTag tag = (RepositoryTag) in.getBody();


log.info("got tag");
}

};