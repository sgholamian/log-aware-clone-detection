//,temp,Hbase.java,6857,6868,temp,Hbase.java,5801,5813
//,3
public class xxx {
          public void onComplete(Void o) {
            deleteAllTs_result result = new deleteAllTs_result();
            try {
              fcall.sendResponse(fb, result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
            } catch (org.apache.thrift.transport.TTransportException e) {
              _LOGGER.error("TTransportException writing to internal frame buffer", e);
              fb.close();
            } catch (java.lang.Exception e) {
              _LOGGER.error("Exception writing to internal frame buffer", e);
              onError(e);
            }
          }

};