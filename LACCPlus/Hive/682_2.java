//,temp,ThriftHiveMetastore.java,29158,29170,temp,ThriftHiveMetastore.java,28070,28081
//,3
public class xxx {
          public void onComplete(Void o) {
            create_table_with_constraints_result result = new create_table_with_constraints_result();
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