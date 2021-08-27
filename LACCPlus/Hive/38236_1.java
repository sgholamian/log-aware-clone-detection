//,temp,ThriftHiveMetastore.java,41182,41194,temp,ThriftHiveMetastore.java,38566,38578
//,2
public class xxx {
          public void onComplete(WMCreateOrUpdateMappingResponse o) {
            create_or_update_wm_mapping_result result = new create_or_update_wm_mapping_result();
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