//,temp,ThriftHiveMetastore.java,35968,35980,temp,ThriftHiveMetastore.java,35574,35586
//,3
public class xxx {
          public void onComplete(java.util.List<Role> o) {
            list_roles_result result = new list_roles_result();
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