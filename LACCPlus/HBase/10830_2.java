//,temp,THBaseService.java,4871,4883,temp,Hbase.java,7243,7256
//,3
public class xxx {
          public void onComplete(java.lang.Integer o) {
            scannerOpen_result result = new scannerOpen_result();
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