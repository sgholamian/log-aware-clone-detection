//,temp,ThriftHiveMetastore.java,40740,40752,temp,ThriftHiveMetastore.java,40590,40602
//,2
public class xxx {
          public void onComplete(WMDropResourcePlanResponse o) {
            drop_resource_plan_result result = new drop_resource_plan_result();
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