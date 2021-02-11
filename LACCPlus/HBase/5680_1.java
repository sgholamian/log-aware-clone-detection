//,temp,THBaseService.java,7666,7678,temp,Hbase.java,5341,5352
//,3
public class xxx {
          public void onComplete(java.util.List<TNamespaceDescriptor> o) {
            listNamespaceDescriptors_result result = new listNamespaceDescriptors_result();
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