//,temp,ThriftHiveMetastore.java,27271,27284,temp,TCLIService.java,2752,2764
//,3
public class xxx {
          public void onComplete(TGetPrimaryKeysResp o) {
            GetPrimaryKeys_result result = new GetPrimaryKeys_result();
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