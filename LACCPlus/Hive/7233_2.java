//,temp,ThriftHiveMetastore.java,43365,43377,temp,ThriftHiveMetastore.java,42631,42643
//,3
public class xxx {
          public void onComplete(GetPartitionsResponse o) {
            get_partitions_with_specs_result result = new get_partitions_with_specs_result();
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