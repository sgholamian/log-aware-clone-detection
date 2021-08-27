//,temp,ThriftHiveMetastore.java,31895,31907,temp,ThriftHiveMetastore.java,31550,31562
//,3
public class xxx {
          public void onComplete(Partition o) {
            get_partition_by_name_result result = new get_partition_by_name_result();
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