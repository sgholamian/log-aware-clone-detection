//,temp,ThriftHiveMetastore.java,36942,36955,temp,ThriftHiveMetastore.java,36816,36827
//,3
public class xxx {
          public void onComplete(Void o) {
            cancel_delegation_token_result result = new cancel_delegation_token_result();
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