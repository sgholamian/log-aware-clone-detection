//,temp,ThriftHiveMetastore.java,26376,26387,temp,TCLIService.java,3179,3191
//,3
public class xxx {
          public void onComplete(TGetDelegationTokenResp o) {
            GetDelegationToken_result result = new GetDelegationToken_result();
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