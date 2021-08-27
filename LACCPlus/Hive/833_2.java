//,temp,ThriftHiveMetastore.java,29617,29629,temp,ThriftHiveMetastore.java,29483,29495
//,3
public class xxx {
          public void onComplete(java.util.List<ExtendedTableInfo> o) {
            get_tables_ext_result result = new get_tables_ext_result();
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