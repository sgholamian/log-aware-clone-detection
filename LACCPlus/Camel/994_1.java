//,temp,GitProducer.java,417,429,temp,GitProducer.java,403,415
//,3
public class xxx {
    protected void doLog(Exchange exchange, String operation) throws Exception {
        Iterable<RevCommit> revCommit = null;
        try {
            if (ObjectHelper.isNotEmpty(endpoint.getBranchName())) {
                git.checkout().setCreateBranch(false).setName(endpoint.getBranchName()).call();
            }
            revCommit = git.log().call();
        } catch (Exception e) {
            LOG.error("There was an error in Git {} operation", operation);
            throw e;
        }
        updateExchange(exchange, revCommit);
    }

};