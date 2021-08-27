//,temp,ThriftHiveMetastore.java,37694,37705,temp,ThriftHiveMetastore.java,37505,37517
//,3
public class xxx {
          public void onComplete(OpenTxnsResponse o) {
            open_txns_result result = new open_txns_result();
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