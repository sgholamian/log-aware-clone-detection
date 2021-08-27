//,temp,ThriftHiveMetastore.java,33840,33852,temp,ThriftHiveMetastore.java,33754,33767
//,3
public class xxx {
          public void onComplete(PrimaryKeysResponse o) {
            get_primary_keys_result result = new get_primary_keys_result();
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