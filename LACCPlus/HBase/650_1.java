//,temp,THBaseService.java,6504,6516,temp,Hbase.java,7113,7124
//,3
public class xxx {
          public void onComplete(java.util.List<TTableName> o) {
            getTableNamesByNamespace_result result = new getTableNamesByNamespace_result();
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