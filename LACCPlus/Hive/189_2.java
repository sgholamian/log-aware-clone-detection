//,temp,ThriftHiveMetastore.java,25897,25909,temp,TCLIService.java,2142,2154
//,3
public class xxx {
          public void onComplete(TCloseSessionResp o) {
            CloseSession_result result = new CloseSession_result();
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