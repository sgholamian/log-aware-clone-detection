//,temp,ThriftHiveMetastore.java,41690,41701,temp,ThriftHiveMetastore.java,40249,40261
//,3
public class xxx {
          public void onComplete(Void o) {
            add_schema_version_result result = new add_schema_version_result();
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