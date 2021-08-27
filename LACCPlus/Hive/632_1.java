//,temp,ThriftHiveMetastore.java,28898,28910,temp,ThriftHiveMetastore.java,27918,27929
//,3
public class xxx {
          public void onComplete(TruncateTableResponse o) {
            truncate_table_req_result result = new truncate_table_req_result();
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