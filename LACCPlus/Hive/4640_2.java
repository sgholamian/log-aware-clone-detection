//,temp,ThriftHiveMetastore.java,38687,38699,temp,ThriftHiveMetastore.java,38159,38170
//,3
public class xxx {
          public void onComplete(Void o) {
            seed_txn_id_result result = new seed_txn_id_result();
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