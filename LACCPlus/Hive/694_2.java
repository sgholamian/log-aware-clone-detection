//,temp,ThriftHiveMetastore.java,29093,29105,temp,ThriftHiveMetastore.java,28766,28777
//,3
public class xxx {
          public void onComplete(Void o) {
            drop_table_with_environment_context_result result = new drop_table_with_environment_context_result();
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