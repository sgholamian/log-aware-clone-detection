//,temp,ThriftHiveMetastore.java,40111,40123,temp,ThriftHiveMetastore.java,39680,39692
//,3
public class xxx {
          public void onComplete(java.lang.String o) {
            get_metastore_db_uuid_result result = new get_metastore_db_uuid_result();
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