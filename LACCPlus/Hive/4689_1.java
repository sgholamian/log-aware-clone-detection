//,temp,ThriftHiveMetastore.java,38748,38760,temp,ThriftHiveMetastore.java,38365,38376
//,3
public class xxx {
          public void onComplete(ShowCompactResponse o) {
            show_compact_result result = new show_compact_result();
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