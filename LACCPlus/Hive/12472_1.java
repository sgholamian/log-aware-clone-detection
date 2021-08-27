//,temp,ThriftHiveMetastore.java,43232,43244,temp,ThriftHiveMetastore.java,41109,41121
//,2
public class xxx {
          public void onComplete(StoredProcedure o) {
            get_stored_procedure_result result = new get_stored_procedure_result();
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