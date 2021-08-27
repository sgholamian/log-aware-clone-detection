//,temp,ThriftHiveMetastore.java,27134,27145,temp,TCLIService.java,2874,2886
//,3
public class xxx {
          public void onComplete(Void o) {
            alter_dataconnector_result result = new alter_dataconnector_result();
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