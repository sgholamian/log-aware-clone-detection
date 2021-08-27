//,temp,sample_4205.java,2,11,temp,sample_3129.java,2,15
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
Message in = exchange.getIn();
RepositoryCommit commit = (RepositoryCommit) in.getBody();
User author = commit.getAuthor();
if (log.isDebugEnabled()) {


log.info("got commit with author sha");
}
}

};