//,temp,ThriftHiveMetastore.java,26517,26529,temp,TCLIService.java,2569,2581
//,3
public class xxx {
          public void onComplete(TGetTableTypesResp o) {
            GetTableTypes_result result = new GetTableTypes_result();
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