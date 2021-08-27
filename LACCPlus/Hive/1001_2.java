//,temp,ThriftHiveMetastore.java,30910,30923,temp,ThriftHiveMetastore.java,30548,30560
//,3
public class xxx {
          public void onComplete(AddPartitionsResult o) {
            add_partitions_req_result result = new add_partitions_req_result();
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