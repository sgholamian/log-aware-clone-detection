//,temp,ThriftHiveMetastore.java,35072,35085,temp,ThriftHiveMetastore.java,32309,32321
//,3
public class xxx {
          public void onComplete(GetPartitionNamesPsResponse o) {
            get_partition_names_ps_req_result result = new get_partition_names_ps_req_result();
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