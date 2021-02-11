//,temp,THBaseService.java,7409,7420,temp,Hbase.java,6921,6932
//,2
public class xxx {
          public void onComplete(Void o) {
            createNamespace_result result = new createNamespace_result();
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