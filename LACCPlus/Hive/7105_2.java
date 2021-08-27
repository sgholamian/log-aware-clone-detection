//,temp,ThriftHiveMetastore.java,42761,42772,temp,ThriftHiveMetastore.java,40176,40188
//,3
public class xxx {
          public void onComplete(WMCreateResourcePlanResponse o) {
            create_resource_plan_result result = new create_resource_plan_result();
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