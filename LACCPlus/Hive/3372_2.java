//,temp,ThriftHiveMetastore.java,36555,36567,temp,ThriftHiveMetastore.java,35902,35915
//,3
public class xxx {
          public void onComplete(java.lang.Boolean o) {
            revoke_role_result result = new revoke_role_result();
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