//,temp,ThriftHiveMetastore.java,26928,26940,temp,ThriftHiveMetastore.java,26856,26867
//,3
public class xxx {
          public void onComplete(Void o) {
            create_dataconnector_result result = new create_dataconnector_result();
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