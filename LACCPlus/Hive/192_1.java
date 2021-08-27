//,temp,ThriftHiveMetastore.java,26239,26251,temp,TCLIService.java,2386,2398
//,3
public class xxx {
          public void onComplete(GetCatalogsResponse o) {
            get_catalogs_result result = new get_catalogs_result();
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