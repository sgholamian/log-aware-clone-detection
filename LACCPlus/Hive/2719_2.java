//,temp,ThriftHiveMetastore.java,35372,35383,temp,ThriftHiveMetastore.java,34479,34491
//,3
public class xxx {
          public void onComplete(SetPartitionsStatsResponse o) {
            update_table_column_statistics_req_result result = new update_table_column_statistics_req_result();
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