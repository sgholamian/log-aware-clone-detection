//,temp,QuorumUtil.java,247,267,temp,QuorumBase.java,390,416
//,3
public class xxx {
    public void shutdown(int id) {
        QuorumPeer qp = getPeer(id).peer;
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
            qp.join(30000);
            if (qp.isAlive()) {
                Assert.fail("QP failed to shutdown in 30 seconds: " + qp.getName());
            }
        } catch (InterruptedException e) {
            LOG.debug("QP interrupted: " + qp.getName(), e);
        }
    }

};