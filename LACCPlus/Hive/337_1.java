//,temp,ThriftHiveMetastore.java,27480,27492,temp,TCLIService.java,3240,3252
//,3
public class xxx {
          public void onComplete(java.util.List<FieldSchema> o) {
            get_fields_result result = new get_fields_result();
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