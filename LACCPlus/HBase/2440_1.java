//,temp,THBaseService.java,6633,6644,temp,THBaseService.java,5196,5207
//,2
public class xxx {
          public void onComplete(Void o) {
            deleteTable_result result = new deleteTable_result();
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