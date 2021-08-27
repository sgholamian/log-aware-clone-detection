//,temp,ThriftHiveMetastore.java,26586,26597,temp,TCLIService.java,2508,2520
//,3
public class xxx {
          public void onComplete(TGetTablesResp o) {
            GetTables_result result = new GetTables_result();
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