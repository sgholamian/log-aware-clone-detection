//,temp,Hbase.java,6256,6268,temp,Hbase.java,5736,5748
//,2
public class xxx {
          public void onComplete(java.util.List<TRowResult> o) {
            getRowsWithColumns_result result = new getRowsWithColumns_result();
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