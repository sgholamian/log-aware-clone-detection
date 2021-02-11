//,temp,Hbase.java,7711,7722,temp,Hbase.java,6061,6073
//,3
public class xxx {
          public void onComplete(Void o) {
            scannerClose_result result = new scannerClose_result();
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