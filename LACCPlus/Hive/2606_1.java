//,temp,ThriftHiveMetastore.java,35150,35163,temp,ThriftHiveMetastore.java,34925,34937
//,3
public class xxx {
          public void onComplete(java.lang.Boolean o) {
            delete_table_column_statistics_result result = new delete_table_column_statistics_result();
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