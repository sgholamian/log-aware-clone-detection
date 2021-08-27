//,temp,ThriftHiveMetastore.java,28222,28233,temp,TCLIService.java,3057,3069
//,3
public class xxx {
          public void onComplete(TGetResultSetMetadataResp o) {
            GetResultSetMetadata_result result = new GetResultSetMetadata_result();
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