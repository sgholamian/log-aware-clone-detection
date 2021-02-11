//,temp,THBaseService.java,7153,7164,temp,Hbase.java,7779,7791
//,3
public class xxx {
          public void onComplete(Void o) {
            addColumnFamily_result result = new addColumnFamily_result();
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