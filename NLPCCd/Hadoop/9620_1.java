//,temp,sample_5444.java,2,11,temp,sample_3854.java,2,11
//,3
public class xxx {
public synchronized void processResult(int rc, String path, Object ctx, Stat stat) {
if (isStaleClient(ctx)) return;
monitorLockNodePending = false;
assert wantToBeInElection : "Got a StatNode result after quitting election";
if (LOG.isDebugEnabled()) {


log.info("statnode result for path connectionstate for");
}
}

};