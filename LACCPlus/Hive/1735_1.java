//,temp,ThriftHiveMetastore.java,33067,33078,temp,ThriftHiveMetastore.java,31826,31838
//,3
public class xxx {
          public void onComplete(Void o) {
            alter_partitions_with_environment_context_result result = new alter_partitions_with_environment_context_result();
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