//,temp,ThriftHiveMetastore.java,43563,43575,temp,ThriftHiveMetastore.java,43499,43510
//,3
public class xxx {
          public void onComplete(java.util.List<java.lang.String> o) {
            get_all_packages_result result = new get_all_packages_result();
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