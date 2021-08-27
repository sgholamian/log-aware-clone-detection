//,temp,ThriftHiveMetastore.java,38030,38042,temp,ThriftHiveMetastore.java,37828,37839
//,3
public class xxx {
          public void onComplete(MaxAllocatedTableWriteIdResponse o) {
            get_max_allocated_table_write_id_result result = new get_max_allocated_table_write_id_result();
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