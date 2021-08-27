//,temp,ThriftHiveMetastore.java,33670,33681,temp,ThriftHiveMetastore.java,33340,33352
//,3
public class xxx {
          public void onComplete(RenamePartitionResponse o) {
            rename_partition_req_result result = new rename_partition_req_result();
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