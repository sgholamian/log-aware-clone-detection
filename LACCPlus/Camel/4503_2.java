//,temp,GitProducer.java,478,499,temp,GitProducer.java,431,452
//,3
public class xxx {
    protected void doPush(Exchange exchange, String operation) throws Exception {
        Iterable<PushResult> result = null;
        try {
            if (ObjectHelper.isEmpty(endpoint.getRemoteName())) {
                throw new IllegalArgumentException("Remote name must be specified to execute " + operation);
            }
            if (ObjectHelper.isNotEmpty(endpoint.getBranchName())) {
                git.checkout().setCreateBranch(false).setName(endpoint.getBranchName()).call();
            }
            if (ObjectHelper.isNotEmpty(endpoint.getUsername()) && ObjectHelper.isNotEmpty(endpoint.getPassword())) {
                UsernamePasswordCredentialsProvider credentials
                        = new UsernamePasswordCredentialsProvider(endpoint.getUsername(), endpoint.getPassword());
                result = git.push().setCredentialsProvider(credentials).setRemote(endpoint.getRemoteName()).call();
            } else {
                result = git.push().setRemote(endpoint.getRemoteName()).call();
            }
        } catch (Exception e) {
            LOG.error("There was an error in Git {} operation", operation);
            throw e;
        }
        updateExchange(exchange, result);
    }

};