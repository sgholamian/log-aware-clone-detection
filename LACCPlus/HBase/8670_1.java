//,temp,Hbase.java,7573,7585,temp,Hbase.java,6723,6736
//,3
public class xxx {
          public void onComplete(java.util.List<TRowResult> o) {
            scannerGet_result result = new scannerGet_result();
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