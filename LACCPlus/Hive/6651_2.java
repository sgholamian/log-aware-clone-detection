//,temp,ThriftHiveMetastore.java,42102,42113,temp,ThriftHiveMetastore.java,42037,42049
//,3
public class xxx {
          public void onComplete(FindSchemasByColsResp o) {
            get_schemas_by_cols_result result = new get_schemas_by_cols_result();
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