//,temp,ThriftHiveMetastore.java,39741,39753,temp,ThriftHiveMetastore.java,39063,39074
//,3
public class xxx {
          public void onComplete(CmRecycleResponse o) {
            cm_recycle_result result = new cm_recycle_result();
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