//,temp,ThriftHiveMetastore.java,39620,39631,temp,ThriftHiveMetastore.java,39559,39571
//,3
public class xxx {
          public void onComplete(Void o) {
            flushCache_result result = new flushCache_result();
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