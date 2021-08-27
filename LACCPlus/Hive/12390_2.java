//,temp,ThriftHiveMetastore.java,43430,43442,temp,ThriftHiveMetastore.java,42696,42708
//,2
public class xxx {
          public void onComplete(ScheduledQueryPollResponse o) {
            scheduled_query_poll_result result = new scheduled_query_poll_result();
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