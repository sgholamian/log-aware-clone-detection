//,temp,ThriftHiveMetastore.java,42974,42985,temp,ThriftHiveMetastore.java,42310,42322
//,3
public class xxx {
          public void onComplete(SerDeInfo o) {
            get_serde_result result = new get_serde_result();
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