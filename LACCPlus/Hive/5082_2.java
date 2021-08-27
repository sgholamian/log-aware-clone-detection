//,temp,ThriftHiveMetastore.java,39498,39510,temp,ThriftHiveMetastore.java,39127,39138
//,3
public class xxx {
          public void onComplete(Void o) {
            mark_compacted_result result = new mark_compacted_result();
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