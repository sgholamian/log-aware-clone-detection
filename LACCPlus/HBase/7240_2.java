//,temp,THBaseService.java,5917,5929,temp,Hbase.java,7779,7791
//,2
public class xxx {
          public void onComplete(TRegionInfo o) {
            getRegionInfo_result result = new getRegionInfo_result();
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