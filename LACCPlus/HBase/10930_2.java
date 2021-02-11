//,temp,THBaseService.java,5324,5336,temp,Hbase.java,5147,5158
//,3
public class xxx {
          public void onComplete(Void o) {
            disableTable_result result = new disableTable_result();
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