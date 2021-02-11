//,temp,THBaseService.java,7345,7356,temp,THBaseService.java,6633,6644
//,2
public class xxx {
          public void onComplete(Void o) {
            modifyTable_result result = new modifyTable_result();
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