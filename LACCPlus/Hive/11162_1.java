//,temp,ThriftHiveMetastore.java,38433,38445,temp,ThriftHiveMetastore.java,38292,38304
//,2
public class xxx {
          public void onComplete(ShowLocksResponse o) {
            show_locks_result result = new show_locks_result();
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