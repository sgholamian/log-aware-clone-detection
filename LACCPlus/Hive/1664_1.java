//,temp,ThriftHiveMetastore.java,32999,33010,temp,ThriftHiveMetastore.java,32793,32805
//,3
public class xxx {
          public void onComplete(Void o) {
            alter_partitions_result result = new alter_partitions_result();
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