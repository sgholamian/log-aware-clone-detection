//,temp,Hbase.java,7049,7060,temp,Hbase.java,5931,5943
//,3
public class xxx {
          public void onComplete(Void o) {
            incrementRows_result result = new incrementRows_result();
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