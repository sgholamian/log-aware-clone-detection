//,temp,ThriftHiveMetastore.java,42170,42181,temp,ThriftHiveMetastore.java,41762,41774
//,3
public class xxx {
          public void onComplete(Void o) {
            set_schema_version_state_result result = new set_schema_version_state_result();
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