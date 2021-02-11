//,temp,Hbase.java,7507,7520,temp,Hbase.java,7113,7124
//,3
public class xxx {
          public void onComplete(Void o) {
            deleteAllRowTs_result result = new deleteAllRowTs_result();
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