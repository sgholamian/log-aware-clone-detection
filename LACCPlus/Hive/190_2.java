//,temp,ThriftHiveMetastore.java,26098,26109,temp,TCLIService.java,2264,2276
//,3
public class xxx {
          public void onComplete(TExecuteStatementResp o) {
            ExecuteStatement_result result = new ExecuteStatement_result();
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