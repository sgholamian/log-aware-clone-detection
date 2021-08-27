//,temp,ThriftHiveMetastore.java,30181,30193,temp,ThriftHiveMetastore.java,29763,29774
//,3
public class xxx {
          public void onComplete(Void o) {
            update_creation_metadata_result result = new update_creation_metadata_result();
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