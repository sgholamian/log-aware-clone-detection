//,temp,THBaseService.java,6243,6256,temp,THBaseService.java,4871,4883
//,3
public class xxx {
          public void onComplete(java.util.List<java.lang.Boolean> o) {
            existsAll_result result = new existsAll_result();
            result.success = o;
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