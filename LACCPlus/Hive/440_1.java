//,temp,ThriftHiveMetastore.java,28146,28157,temp,TCLIService.java,3423,3435
//,3
public class xxx {
          public void onComplete(Void o) {
            create_table_req_result result = new create_table_req_result();
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