//,temp,ThriftHiveMetastore.java,42566,42578,temp,ThriftHiveMetastore.java,42502,42513
//,3
public class xxx {
          public void onComplete(Void o) {
            add_runtime_stats_result result = new add_runtime_stats_result();
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