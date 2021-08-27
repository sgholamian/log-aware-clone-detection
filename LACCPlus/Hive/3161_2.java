//,temp,ThriftHiveMetastore.java,36424,36437,temp,ThriftHiveMetastore.java,36033,36045
//,3
public class xxx {
          public void onComplete(GrantRevokeRoleResponse o) {
            grant_revoke_role_result result = new grant_revoke_role_result();
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