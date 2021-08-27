//,temp,ThriftHiveMetastore.java,33475,33487,temp,ThriftHiveMetastore.java,33409,33422
//,3
public class xxx {
          public void onComplete(java.lang.String o) {
            get_config_value_result result = new get_config_value_result();
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