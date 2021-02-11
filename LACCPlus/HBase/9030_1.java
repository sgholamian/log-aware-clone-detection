//,temp,THBaseService.java,6439,6451,temp,Hbase.java,5277,5288
//,3
public class xxx {
          public void onComplete(java.util.List<TTableName> o) {
            getTableNamesByPattern_result result = new getTableNamesByPattern_result();
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