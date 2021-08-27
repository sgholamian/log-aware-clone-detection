//,temp,ThriftHiveMetastore.java,35150,35163,temp,ThriftHiveMetastore.java,34925,34937
//,3
public class xxx {
          public void onComplete(AggrStats o) {
            get_aggr_stats_for_result result = new get_aggr_stats_for_result();
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