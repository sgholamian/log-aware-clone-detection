//,temp,ThriftHiveMetastore.java,41900,41912,temp,ThriftHiveMetastore.java,39928,39940
//,3
public class xxx {
          public void onComplete(PutFileMetadataResult o) {
            put_file_metadata_result result = new put_file_metadata_result();
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