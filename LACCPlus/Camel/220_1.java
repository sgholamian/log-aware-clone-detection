//,temp,Calculator.java,1059,1072,temp,Calculator.java,817,830
//,2
public class xxx {
                    public void onComplete(java.lang.Integer o) {
                        alltypes_result result = new alltypes_result();
                        result.success = o;
                        result.setSuccessIsSet(true);
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