//,temp,ThriftHiveMetastore.java,39498,39510,temp,ThriftHiveMetastore.java,39127,39138
//,3
public class xxx {
          public void onComplete(NotificationEventsCountResponse o) {
            get_notification_events_count_result result = new get_notification_events_count_result();
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