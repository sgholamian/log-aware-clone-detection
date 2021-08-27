//,temp,ThriftHiveMetastore.java,30254,30266,temp,ThriftHiveMetastore.java,29976,29987
//,3
public class xxx {
          public void onComplete(Partition o) {
            add_partition_with_environment_context_result result = new add_partition_with_environment_context_result();
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