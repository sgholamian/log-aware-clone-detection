//,temp,CommitProcessor.java,421,431,temp,CommitProcessor.java,410,419
//,3
public class xxx {
    public void commit(Request request) {
        if (stopped || request == null) {
            return;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Committing request:: " + request);
        }
        committedRequests.add(request);
        wakeup();
    }

};