//,temp,THBaseService.java,5788,5799,temp,THBaseService.java,4936,4948
//,3
public class xxx {
          public void onComplete(Void o) {
            mutateRow_result result = new mutateRow_result();
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