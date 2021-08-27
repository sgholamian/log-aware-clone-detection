//,temp,Calculator.java,885,898,temp,Calculator.java,752,763
//,3
public class xxx {
                    public void onComplete(Void o) {
                        ping_result result = new ping_result();
                        try {
                            fcall.sendResponse(fb, result, org.apache.thrift.protocol.TMessageType.REPLY, seqid);
                        } catch (org.apache.thrift.transport.TTransportException e) {
                            _LOGGER.error("TTransportException writing to internal frame buffer", e);
                            fb.close();
                        } catch (java.lang.Exception e) {
                            _LOGGER.error("Exception writing to internal frame buffer", e);
                            onError(e);
                        }
                    }

};