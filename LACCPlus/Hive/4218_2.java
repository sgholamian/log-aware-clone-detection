//,temp,ThriftHiveMetastore.java,37888,37900,temp,ThriftHiveMetastore.java,37762,37775
//,3
public class xxx {
          public void onComplete(java.lang.Long o) {
            get_latest_txnid_in_conflict_result result = new get_latest_txnid_in_conflict_result();
            result.success = o;
            result.setSuccessIsSet(true);
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