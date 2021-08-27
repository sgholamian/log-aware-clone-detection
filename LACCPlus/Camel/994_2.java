//,temp,GitProducer.java,417,429,temp,GitProducer.java,403,415
//,3
public class xxx {
    protected void doStatus(Exchange exchange, String operation) throws Exception {
        Status status = null;
        try {
            if (ObjectHelper.isNotEmpty(endpoint.getBranchName())) {
                git.checkout().setCreateBranch(false).setName(endpoint.getBranchName()).call();
            }
            status = git.status().call();
        } catch (Exception e) {
            LOG.error("There was an error in Git {} operation", operation);
            throw e;
        }
        updateExchange(exchange, status);
    }

};