//,temp,ThriftHiveMetastore.java,41969,41980,temp,ThriftHiveMetastore.java,40813,40825
//,3
public class xxx {
          public void onComplete(Void o) {
            drop_schema_version_result result = new drop_schema_version_result();
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