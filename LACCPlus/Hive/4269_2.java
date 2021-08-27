//,temp,ThriftHiveMetastore.java,38030,38042,temp,ThriftHiveMetastore.java,37828,37839
//,3
public class xxx {
          public void onComplete(Void o) {
            repl_tbl_writeid_state_result result = new repl_tbl_writeid_state_result();
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