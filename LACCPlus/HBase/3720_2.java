//,temp,THBaseService.java,5196,5207,temp,Hbase.java,5083,5094
//,2
public class xxx {
          public void onComplete(Void o) {
            enableTable_result result = new enableTable_result();
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