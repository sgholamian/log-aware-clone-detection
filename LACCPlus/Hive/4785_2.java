//,temp,ThriftHiveMetastore.java,38942,38953,temp,ThriftHiveMetastore.java,38877,38889
//,3
public class xxx {
          public void onComplete(OptionalCompactionInfoStruct o) {
            find_next_compact_result result = new find_next_compact_result();
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