//,temp,THBaseService.java,6955,6968,temp,THBaseService.java,6439,6451
//,3
public class xxx {
          public void onComplete(java.lang.Boolean o) {
            isTableDisabled_result result = new isTableDisabled_result();
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