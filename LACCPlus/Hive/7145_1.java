//,temp,ThriftHiveMetastore.java,43164,43175,temp,ThriftHiveMetastore.java,42905,42917
//,3
public class xxx {
          public void onComplete(Void o) {
            create_stored_procedure_result result = new create_stored_procedure_result();
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