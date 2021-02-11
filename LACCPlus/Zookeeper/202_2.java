//,temp,QuorumPeerMainTest.java,1206,1217,temp,QuorumPeerMainTest.java,1179,1199
//,3
public class xxx {
                @Override
                public void start() {
                    if (!Boolean.getBoolean(LearnerHandler.FORCE_SNAP_SYNC)) {
                        return;
                    }
                    final String value = "2";
                    LOG.info("start forwarding, set {} to {}", nodePath, value);
                    // use async, otherwise it will block the logLock in
                    // ZKDatabase and the setData request will timeout
                    try {
                        leaderZk.setData(nodePath, value.getBytes(), -1,
                                new AsyncCallback.StatCallback() {
                            public void processResult(int rc, String path,
                                   Object ctx, Stat stat) {}
                        }, null);
                        // wait for the setData txn being populated
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        LOG.error("error when set {} to {}", nodePath, value, e);
                    }
                }

};