//,temp,ThriftHiveMetastore.java,32516,32528,temp,ThriftHiveMetastore.java,32378,32390
//,3
public class xxx {
          public void onComplete(java.util.List<PartitionSpec> o) {
            get_part_specs_by_filter_result result = new get_part_specs_by_filter_result();
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