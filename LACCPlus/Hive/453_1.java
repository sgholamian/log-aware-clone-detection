//,temp,ThriftHiveMetastore.java,27994,28005,temp,TCLIService.java,3301,3313
//,3
public class xxx {
          public void onComplete(Void o) {
            create_table_with_environment_context_result result = new create_table_with_environment_context_result();
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