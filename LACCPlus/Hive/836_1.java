//,temp,ThriftHiveMetastore.java,29690,29702,temp,ThriftHiveMetastore.java,28426,28437
//,3
public class xxx {
          public void onComplete(Materialization o) {
            get_materialization_invalidation_info_result result = new get_materialization_invalidation_info_result();
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