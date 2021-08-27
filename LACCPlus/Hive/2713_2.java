//,temp,ThriftHiveMetastore.java,35440,35452,temp,ThriftHiveMetastore.java,35304,35315
//,3
public class xxx {
          public void onComplete(Void o) {
            drop_function_result result = new drop_function_result();
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