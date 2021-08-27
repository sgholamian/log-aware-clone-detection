//,temp,ThriftHiveMetastore.java,40955,40967,temp,ThriftHiveMetastore.java,40663,40675
//,2
public class xxx {
          public void onComplete(WMCreatePoolResponse o) {
            create_wm_pool_result result = new create_wm_pool_result();
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