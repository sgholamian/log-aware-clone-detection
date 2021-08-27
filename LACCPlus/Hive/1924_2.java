//,temp,ThriftHiveMetastore.java,33605,33617,temp,ThriftHiveMetastore.java,32585,32597
//,3
public class xxx {
          public void onComplete(PartitionsByExprResult o) {
            get_partitions_by_expr_result result = new get_partitions_by_expr_result();
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