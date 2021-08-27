//,temp,ThriftHiveMetastore.java,30112,30124,temp,ThriftHiveMetastore.java,28494,28505
//,3
public class xxx {
          public void onComplete(AlterTableResponse o) {
            alter_table_req_result result = new alter_table_req_result();
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