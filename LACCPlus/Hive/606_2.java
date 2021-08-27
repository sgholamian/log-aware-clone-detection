//,temp,ThriftHiveMetastore.java,28963,28975,temp,ThriftHiveMetastore.java,28834,28845
//,3
public class xxx {
          public void onComplete(Void o) {
            truncate_table_result result = new truncate_table_result();
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