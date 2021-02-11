//,temp,QuorumPeerMainTest.java,1206,1217,temp,DatadirCleanupManager.java,136,145
//,3
public class xxx {
                @Override
                public void start() {
                    String value = "3";
                    LOG.info("before sending snapshot, set {} to {}",
                            nodePath, value);
                    try {
                        leaderZk.setData(nodePath, value.getBytes(), -1);
                        LOG.info("successfully set {} to {}", nodePath, value);
                    } catch (Exception e) {
                        LOG.error("error when set {} to {}, {}", nodePath, value, e);
                    }
                }

};