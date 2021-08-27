//,temp,ThriftHiveMetastore.java,37566,37577,temp,ThriftHiveMetastore.java,37444,37456
//,3
public class xxx {
          public void onComplete(GetOpenTxnsInfoResponse o) {
            get_open_txns_info_result result = new get_open_txns_info_result();
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