//,temp,THBaseService.java,6374,6386,temp,THBaseService.java,5066,5077
//,3
public class xxx {
          public void onComplete(java.util.List<TTableDescriptor> o) {
            getTableDescriptorsByNamespace_result result = new getTableDescriptorsByNamespace_result();
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