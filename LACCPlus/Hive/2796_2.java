//,temp,ThriftHiveMetastore.java,35639,35652,temp,ThriftHiveMetastore.java,35505,35517
//,3
public class xxx {
          public void onComplete(Function o) {
            get_function_result result = new get_function_result();
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