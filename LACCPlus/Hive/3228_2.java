//,temp,ThriftHiveMetastore.java,36358,36371,temp,ThriftHiveMetastore.java,36098,36110
//,3
public class xxx {
          public void onComplete(GetPrincipalsInRoleResponse o) {
            get_principals_in_role_result result = new get_principals_in_role_result();
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