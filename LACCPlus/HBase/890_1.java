//,temp,THBaseService.java,7473,7484,temp,THBaseService.java,5651,5663
//,3
public class xxx {
          public void onComplete(Void o) {
            modifyNamespace_result result = new modifyNamespace_result();
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