//,temp,Hbase.java,7909,7922,temp,Hbase.java,7177,7190
//,2
public class xxx {
          public void onComplete(java.lang.Boolean o) {
            checkAndPut_result result = new checkAndPut_result();
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