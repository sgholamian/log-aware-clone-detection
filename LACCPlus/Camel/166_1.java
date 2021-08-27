//,temp,GitProducer.java,391,401,temp,GitProducer.java,379,389
//,2
public class xxx {
    protected void doDeleteBranch(Exchange exchange, String operation) throws Exception {
        if (ObjectHelper.isEmpty(endpoint.getBranchName())) {
            throw new IllegalArgumentException("Branch Name must be specified to execute " + operation);
        }
        try {
            git.branchDelete().setBranchNames(endpoint.getBranchName()).call();
        } catch (Exception e) {
            LOG.error("There was an error in Git {} operation", operation);
            throw e;
        }
    }

};