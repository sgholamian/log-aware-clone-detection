//,temp,THBaseService.java,5389,5402,temp,Hbase.java,6723,6736
//,2
public class xxx {
          public void onComplete(java.lang.Boolean o) {
            checkAndDelete_result result = new checkAndDelete_result();
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