//,temp,ThriftHiveMetastore.java,26448,26460,temp,TCLIService.java,2630,2642
//,3
public class xxx {
          public void onComplete(TGetColumnsResp o) {
            GetColumns_result result = new GetColumns_result();
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