//,temp,ThriftHiveMetastore.java,35771,35783,temp,ThriftHiveMetastore.java,33978,33990
//,3
public class xxx {
          public void onComplete(UniqueConstraintsResponse o) {
            get_unique_constraints_result result = new get_unique_constraints_result();
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