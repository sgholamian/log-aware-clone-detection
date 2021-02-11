//,temp,QuorumUtil.java,247,267,temp,QuorumBase.java,390,416
//,3
public class xxx {
    public static void shutdown(QuorumPeer qp) {
        if (qp == null) {
            return;
        }
        try {
            LOG.info("Shutting down quorum peer " + qp.getName());
            qp.shutdown();
            Election e = qp.getElectionAlg();
            if (e != null) {
                LOG.info("Shutting down leader election " + qp.getName());
                e.shutdown();
            } else {
                LOG.info("No election available to shutdown " + qp.getName());
            }
            LOG.info("Waiting for " + qp.getName() + " to exit thread");
            long readTimeout = qp.getTickTime() * qp.getInitLimit();
            long connectTimeout = qp.getTickTime() * qp.getSyncLimit();
            long maxTimeout = Math.max(readTimeout, connectTimeout);
            maxTimeout = Math.max(maxTimeout, ClientBase.CONNECTION_TIMEOUT);
            qp.join(maxTimeout * 2);
            if (qp.isAlive()) {
                Assert.fail("QP failed to shutdown in " + (maxTimeout * 2) + " seconds: " + qp.getName());
            }
        } catch (InterruptedException e) {
            LOG.debug("QP interrupted: " + qp.getName(), e);
        }
    }

};