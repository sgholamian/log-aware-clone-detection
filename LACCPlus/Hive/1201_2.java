//,temp,ThriftHiveMetastore.java,31404,31416,temp,ThriftHiveMetastore.java,31327,31339
//,3
public class xxx {
          public void onComplete(Partition o) {
            exchange_partition_result result = new exchange_partition_result();
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