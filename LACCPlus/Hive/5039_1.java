//,temp,ThriftHiveMetastore.java,39315,39327,temp,ThriftHiveMetastore.java,39255,39266
//,3
public class xxx {
          public void onComplete(GetLatestCommittedCompactionInfoResponse o) {
            get_latest_committed_compaction_info_result result = new get_latest_committed_compaction_info_result();
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