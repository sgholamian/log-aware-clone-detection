//,temp,ThriftHiveMetastore.java,39376,39388,temp,ThriftHiveMetastore.java,37630,37641
//,3
public class xxx {
          public void onComplete(NotificationEventResponse o) {
            get_next_notification_result result = new get_next_notification_result();
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