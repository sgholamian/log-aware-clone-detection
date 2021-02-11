//,temp,THBaseService.java,7021,7034,temp,THBaseService.java,4936,4948
//,3
public class xxx {
          public void onComplete(java.lang.Boolean o) {
            isTableAvailable_result result = new isTableAvailable_result();
            result.success = o;
            result.setSuccessIsSet(true);
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