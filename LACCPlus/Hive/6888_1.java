//,temp,ThriftHiveMetastore.java,42440,42453,temp,ThriftHiveMetastore.java,41259,41271
//,3
public class xxx {
          public void onComplete(java.lang.Boolean o) {
            heartbeat_lock_materialization_rebuild_result result = new heartbeat_lock_materialization_rebuild_result();
            result.success = o;
            result.setSuccessIsSet(true);
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