//,temp,THBaseService.java,7537,7548,temp,THBaseService.java,4805,4818
//,3
public class xxx {
          public void onComplete(Void o) {
            deleteNamespace_result result = new deleteNamespace_result();
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