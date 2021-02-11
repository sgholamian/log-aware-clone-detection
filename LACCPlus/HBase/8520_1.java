//,temp,Hbase.java,5405,5417,temp,Hbase.java,5277,5288
//,3
public class xxx {
          public void onComplete(java.util.List<java.nio.ByteBuffer> o) {
            getTableNames_result result = new getTableNames_result();
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