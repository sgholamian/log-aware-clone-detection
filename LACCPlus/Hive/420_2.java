//,temp,ThriftHiveMetastore.java,27553,27565,temp,TCLIService.java,3118,3130
//,3
public class xxx {
          public void onComplete(TFetchResultsResp o) {
            FetchResults_result result = new FetchResults_result();
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