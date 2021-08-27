//,temp,ThriftHiveMetastore.java,41481,41492,temp,ThriftHiveMetastore.java,40383,40395
//,3
public class xxx {
          public void onComplete(WMGetAllResourcePlanResponse o) {
            get_all_resource_plans_result result = new get_all_resource_plans_result();
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