//,temp,Hbase.java,7642,7654,temp,Hbase.java,7375,7388
//,3
public class xxx {
          public void onComplete(java.lang.Integer o) {
            scannerOpenWithPrefix_result result = new scannerOpenWithPrefix_result();
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