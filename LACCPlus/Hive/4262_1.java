//,temp,ThriftHiveMetastore.java,38095,38106,temp,ThriftHiveMetastore.java,34633,34645
//,3
public class xxx {
          public void onComplete(Void o) {
            seed_write_id_result result = new seed_write_id_result();
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