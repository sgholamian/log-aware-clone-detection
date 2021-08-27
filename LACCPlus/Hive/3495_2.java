//,temp,ThriftHiveMetastore.java,36880,36893,temp,ThriftHiveMetastore.java,34710,34722
//,3
public class xxx {
          public void onComplete(ColumnStatistics o) {
            get_partition_column_statistics_result result = new get_partition_column_statistics_result();
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