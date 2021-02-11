//,temp,THBaseService.java,5852,5864,temp,THBaseService.java,5720,5731
//,3
public class xxx {
          public void onComplete(Void o) {
            closeScanner_result result = new closeScanner_result();
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