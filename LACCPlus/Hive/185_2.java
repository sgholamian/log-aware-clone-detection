//,temp,ThriftHiveMetastore.java,25962,25973,temp,TCLIService.java,2325,2337
//,3
public class xxx {
          public void onComplete(TGetTypeInfoResp o) {
            GetTypeInfo_result result = new GetTypeInfo_result();
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