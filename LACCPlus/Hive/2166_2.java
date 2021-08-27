//,temp,ThriftHiveMetastore.java,34401,34414,temp,ThriftHiveMetastore.java,33909,33921
//,3
public class xxx {
          public void onComplete(ForeignKeysResponse o) {
            get_foreign_keys_result result = new get_foreign_keys_result();
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