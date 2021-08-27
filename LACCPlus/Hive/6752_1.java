//,temp,ThriftHiveMetastore.java,42379,42391,temp,ThriftHiveMetastore.java,42242,42253
//,3
public class xxx {
          public void onComplete(LockResponse o) {
            get_lock_materialization_rebuild_result result = new get_lock_materialization_rebuild_result();
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