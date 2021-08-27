//,temp,ThriftHiveMetastore.java,35705,35718,temp,ThriftHiveMetastore.java,34116,34128
//,3
public class xxx {
          public void onComplete(DefaultConstraintsResponse o) {
            get_default_constraints_result result = new get_default_constraints_result();
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