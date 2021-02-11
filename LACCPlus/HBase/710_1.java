//,temp,THBaseService.java,7217,7228,temp,THBaseService.java,6955,6968
//,3
public class xxx {
          public void onComplete(Void o) {
            deleteColumnFamily_result result = new deleteColumnFamily_result();
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