//,temp,ThriftHiveMetastore.java,27345,27358,temp,ThriftHiveMetastore.java,27202,27214
//,3
public class xxx {
          public void onComplete(java.lang.Boolean o) {
            drop_type_result result = new drop_type_result();
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