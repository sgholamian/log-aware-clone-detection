//,temp,ThriftHiveMetastore.java,39806,39818,temp,ThriftHiveMetastore.java,39437,39449
//,2
public class xxx {
          public void onComplete(CurrentNotificationEventId o) {
            get_current_notificationEventId_result result = new get_current_notificationEventId_result();
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