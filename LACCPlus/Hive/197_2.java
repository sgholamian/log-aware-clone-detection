//,temp,ThriftHiveMetastore.java,26026,26037,temp,TCLIService.java,2447,2459
//,3
public class xxx {
          public void onComplete(TGetSchemasResp o) {
            GetSchemas_result result = new GetSchemas_result();
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