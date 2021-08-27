//,temp,GitProducer.java,292,308,temp,GitProducer.java,274,290
//,2
public class xxx {
    protected void doAdd(Exchange exchange, String operation) throws Exception {
        String fileName = null;
        if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(GitConstants.GIT_FILE_NAME))) {
            fileName = exchange.getIn().getHeader(GitConstants.GIT_FILE_NAME, String.class);
        } else {
            throw new IllegalArgumentException("File name must be specified to execute " + operation);
        }
        try {
            if (ObjectHelper.isNotEmpty(endpoint.getBranchName())) {
                git.checkout().setCreateBranch(false).setName(endpoint.getBranchName()).call();
            }
            git.add().addFilepattern(fileName).call();
        } catch (Exception e) {
            LOG.error("There was an error in Git {} operation", operation);
            throw e;
        }
    }

};