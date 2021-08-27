//,temp,ThriftHiveMetastore.java,35228,35239,temp,ThriftHiveMetastore.java,34047,34059
//,3
public class xxx {
          public void onComplete(NotNullConstraintsResponse o) {
            get_not_null_constraints_result result = new get_not_null_constraints_result();
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