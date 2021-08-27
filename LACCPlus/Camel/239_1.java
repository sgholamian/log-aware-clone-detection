//,temp,CommitConsumerTest.java,52,60,temp,CommitConsumerBeginningTest.java,61,69
//,2
public class xxx {
        @Override
        public void process(Exchange exchange) throws Exception {
            String author = exchange.getMessage().getHeader(GitHubConstants.GITHUB_COMMIT_AUTHOR, String.class);
            String sha = exchange.getMessage().getHeader(GitHubConstants.GITHUB_COMMIT_SHA, String.class);
            if (log.isDebugEnabled()) {
                log.debug("Got commit with author: " + author + ": SHA "
                          + sha);
            }
        }

};