//,temp,THBaseService.java,5260,5271,temp,Hbase.java,6519,6530
//,2
public class xxx {
          public void onComplete(Void o) {
            deleteSingle_result result = new deleteSingle_result();
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