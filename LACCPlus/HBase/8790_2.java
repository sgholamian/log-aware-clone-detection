//,temp,THBaseService.java,5130,5143,temp,Hbase.java,6519,6530
//,3
public class xxx {
          public void onComplete(Void o) {
            mutateRowTs_result result = new mutateRowTs_result();
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