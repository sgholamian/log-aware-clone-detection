//,temp,NettyServerCnxnFactory.java,529,545,temp,AuthFastLeaderElection.java,225,241
//,3
public class xxx {
            boolean saveChallenge(long tag, long challenge) {
                Semaphore s = challengeMutex.get(tag);
                if (s != null) {
                        synchronized (Messenger.this) {
                            challengeMap.put(tag, challenge);
                            challengeMutex.remove(tag);
                        }

                
                        s.release();
                } else {
                    LOG.error("No challenge mutex object");
                }
                

                return true;
            }

};