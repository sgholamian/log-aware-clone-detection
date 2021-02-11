//,temp,CommitProcessor.java,421,431,temp,CommitProcessor.java,410,419
//,3
public class xxx {
    @Override
    public void processRequest(Request request) {
        if (stopped) {
            return;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Processing request:: " + request);
        }
        queuedRequests.add(request);
        wakeup();
    }

};