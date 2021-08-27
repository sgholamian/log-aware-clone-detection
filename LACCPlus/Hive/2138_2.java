//,temp,ThriftHiveMetastore.java,34323,34336,temp,ThriftHiveMetastore.java,34254,34266
//,3
public class xxx {
          public void onComplete(AllTableConstraintsResponse o) {
            get_all_table_constraints_result result = new get_all_table_constraints_result();
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