//,temp,THBaseService.java,6633,6644,temp,Hbase.java,5801,5813
//,3
public class xxx {
          public void onComplete(java.util.List<TCell> o) {
            getVer_result result = new getVer_result();
            result.success = o;
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