//,temp,ThriftHiveMetastore.java,40886,40898,temp,ThriftHiveMetastore.java,39867,39879
//,2
public class xxx {
          public void onComplete(GetFileMetadataResult o) {
            get_file_metadata_result result = new get_file_metadata_result();
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