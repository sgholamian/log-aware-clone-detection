//,temp,ThriftHiveMetastore.java,40318,40330,temp,ThriftHiveMetastore.java,39989,40001
//,2
public class xxx {
          public void onComplete(WMGetActiveResourcePlanResponse o) {
            get_active_resource_plan_result result = new get_active_resource_plan_result();
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