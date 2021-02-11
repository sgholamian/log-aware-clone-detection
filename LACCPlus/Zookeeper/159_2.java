//,temp,QuorumPeerMainTest.java,1206,1217,temp,DatadirCleanupManager.java,136,145
//,3
public class xxx {
        @Override
        public void run() {
            LOG.info("Purge task started.");
            try {
                PurgeTxnLog.purge(logsDir, snapsDir, snapRetainCount);
            } catch (Exception e) {
                LOG.error("Error occurred while purging.", e);
            }
            LOG.info("Purge task completed.");
        }

};