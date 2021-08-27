//,temp,ThriftHiveMetastore.java,29548,29560,temp,ThriftHiveMetastore.java,29028,29040
//,3
public class xxx {
          public void onComplete(GetTableResult o) {
            get_table_req_result result = new get_table_req_result();
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