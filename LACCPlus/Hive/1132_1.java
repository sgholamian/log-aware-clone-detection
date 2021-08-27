//,temp,ThriftHiveMetastore.java,31120,31132,temp,ThriftHiveMetastore.java,30327,30340
//,3
public class xxx {
          public void onComplete(DropPartitionsResult o) {
            drop_partitions_req_result result = new drop_partitions_req_result();
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