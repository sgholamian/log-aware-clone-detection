//,temp,THBaseService.java,5455,5467,temp,Hbase.java,6921,6932
//,3
public class xxx {
          public void onComplete(Void o) {
            deleteAllRow_result result = new deleteAllRow_result();
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