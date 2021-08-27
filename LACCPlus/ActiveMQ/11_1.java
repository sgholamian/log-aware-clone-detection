//,temp,TransportConnection.java,1118,1126,temp,TransportConnection.java,1101,1110
//,3
public class xxx {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(waitTime);
                            stopAsync();
                            LOG.info("Stopping {} because {}", transport.getRemoteAddress(), reason);
                        } catch (InterruptedException e) {
                        }
                    }

};