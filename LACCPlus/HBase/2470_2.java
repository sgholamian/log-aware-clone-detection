//,temp,THBaseService.java,6697,6708,temp,Hbase.java,6793,6804
//,2
public class xxx {
          public void onComplete(Void o) {
            deleteAll_result result = new deleteAll_result();
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