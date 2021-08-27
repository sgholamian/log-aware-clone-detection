//,temp,ThriftHiveMetastore.java,33540,33552,temp,ThriftHiveMetastore.java,30621,30633
//,3
public class xxx {
          public void onComplete(Partition o) {
            append_partition_with_environment_context_result result = new append_partition_with_environment_context_result();
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