//,temp,ThriftHiveMetastore.java,34994,35007,temp,ThriftHiveMetastore.java,31688,31700
//,3
public class xxx {
          public void onComplete(PartitionsResponse o) {
            get_partitions_req_result result = new get_partitions_req_result();
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