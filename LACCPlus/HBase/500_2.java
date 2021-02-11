//,temp,THBaseService.java,5852,5864,temp,Hbase.java,7177,7190
//,3
public class xxx {
          public void onComplete(java.lang.Integer o) {
            scannerOpenWithScan_result result = new scannerOpenWithScan_result();
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