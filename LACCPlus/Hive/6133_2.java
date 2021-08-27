//,temp,ThriftHiveMetastore.java,41409,41420,temp,ThriftHiveMetastore.java,41332,41344
//,3
public class xxx {
          public void onComplete(WMCreateOrDropTriggerToPoolMappingResponse o) {
            create_or_drop_wm_trigger_to_pool_mapping_result result = new create_or_drop_wm_trigger_to_pool_mapping_result();
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