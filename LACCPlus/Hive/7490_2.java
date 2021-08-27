//,temp,ThriftHiveMetastore.java,43628,43639,temp,ThriftHiveMetastore.java,43103,43115
//,3
public class xxx {
          public void onComplete(GetOpenTxnsResponse o) {
            get_open_txns_req_result result = new get_open_txns_req_result();
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