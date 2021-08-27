//,temp,ThriftHiveMetastore.java,30694,30706,temp,ThriftHiveMetastore.java,28698,28709
//,3
public class xxx {
          public void onComplete(Void o) {
            drop_table_result result = new drop_table_result();
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