//,temp,ThriftHiveMetastore.java,39191,39202,temp,ThriftHiveMetastore.java,39002,39014
//,3
public class xxx {
          public void onComplete(Void o) {
            mark_failed_result result = new mark_failed_result();
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