//,temp,THBaseService.java,7217,7228,temp,Hbase.java,6321,6333
//,3
public class xxx {
          public void onComplete(java.util.List<TRowResult> o) {
            getRowsTs_result result = new getRowsTs_result();
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