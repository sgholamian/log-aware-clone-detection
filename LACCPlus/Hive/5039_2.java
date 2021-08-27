//,temp,ThriftHiveMetastore.java,39315,39327,temp,ThriftHiveMetastore.java,39255,39266
//,3
public class xxx {
          public void onComplete(Void o) {
            set_hadoop_jobid_result result = new set_hadoop_jobid_result();
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