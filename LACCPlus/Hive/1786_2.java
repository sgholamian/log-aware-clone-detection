//,temp,ThriftHiveMetastore.java,33204,33215,temp,ThriftHiveMetastore.java,32862,32874
//,3
public class xxx {
          public void onComplete(GetPartitionsByNamesResult o) {
            get_partitions_by_names_req_result result = new get_partitions_by_names_req_result();
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