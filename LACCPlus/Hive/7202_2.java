//,temp,ThriftHiveMetastore.java,43301,43312,temp,ThriftHiveMetastore.java,43038,43050
//,3
public class xxx {
          public void onComplete(ReplicationMetricList o) {
            get_replication_metrics_result result = new get_replication_metrics_result();
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