//,temp,ThriftHiveMetastore.java,26170,26182,temp,TCLIService.java,2203,2215
//,3
public class xxx {
          public void onComplete(TGetInfoResp o) {
            GetInfo_result result = new GetInfo_result();
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