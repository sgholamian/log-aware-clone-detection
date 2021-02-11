//,temp,THBaseService.java,7281,7292,temp,Hbase.java,7309,7322
//,3
public class xxx {
          public void onComplete(Void o) {
            modifyColumnFamily_result result = new modifyColumnFamily_result();
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