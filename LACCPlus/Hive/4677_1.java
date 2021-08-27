//,temp,ThriftHiveMetastore.java,38809,38820,temp,ThriftHiveMetastore.java,37957,37969
//,3
public class xxx {
          public void onComplete(Void o) {
            add_dynamic_partitions_result result = new add_dynamic_partitions_result();
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