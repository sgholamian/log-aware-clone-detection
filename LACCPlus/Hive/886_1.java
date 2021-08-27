//,temp,ThriftHiveMetastore.java,30044,30055,temp,ThriftHiveMetastore.java,29422,29434
//,3
public class xxx {
          public void onComplete(Void o) {
            alter_table_with_cascade_result result = new alter_table_with_cascade_result();
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