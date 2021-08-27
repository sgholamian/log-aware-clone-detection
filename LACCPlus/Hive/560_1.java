//,temp,ThriftHiveMetastore.java,28562,28573,temp,ThriftHiveMetastore.java,27626,27638
//,3
public class xxx {
          public void onComplete(Void o) {
            add_default_constraint_result result = new add_default_constraint_result();
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