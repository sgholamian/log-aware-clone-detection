//,temp,ThriftHiveMetastore.java,32931,32942,temp,ThriftHiveMetastore.java,32723,32736
//,3
public class xxx {
          public void onComplete(java.lang.Integer o) {
            get_num_partitions_by_filter_result result = new get_num_partitions_by_filter_result();
            result.success = o;
            result.setSuccessIsSet(true);
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