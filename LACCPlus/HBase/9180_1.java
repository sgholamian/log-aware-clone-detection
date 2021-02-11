//,temp,THBaseService.java,7087,7100,temp,THBaseService.java,6761,6772
//,3
public class xxx {
          public void onComplete(java.lang.Boolean o) {
            isTableAvailableWithSplit_result result = new isTableAvailableWithSplit_result();
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