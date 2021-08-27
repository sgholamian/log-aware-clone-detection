//,temp,ThriftHiveMetastore.java,31189,31201,temp,ThriftHiveMetastore.java,30840,30853
//,3
public class xxx {
          public void onComplete(java.lang.Boolean o) {
            drop_partition_result result = new drop_partition_result();
            result.success = o;
            result.setSuccessIsSet(true);
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