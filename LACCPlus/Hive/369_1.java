//,temp,ThriftHiveMetastore.java,27415,27427,temp,TCLIService.java,2996,3008
//,3
public class xxx {
          public void onComplete(java.util.Map<java.lang.String,Type> o) {
            get_type_all_result result = new get_type_all_result();
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