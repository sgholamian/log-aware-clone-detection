//,temp,THBaseService.java,5982,5994,temp,Hbase.java,7177,7190
//,3
public class xxx {
          public void onComplete(java.util.List<THRegionLocation> o) {
            getAllRegionLocations_result result = new getAllRegionLocations_result();
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