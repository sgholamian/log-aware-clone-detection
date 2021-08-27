//,temp,ThriftHiveMetastore.java,26304,26315,temp,TCLIService.java,2935,2947
//,3
public class xxx {
          public void onComplete(Void o) {
            drop_catalog_result result = new drop_catalog_result();
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