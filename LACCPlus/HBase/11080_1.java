//,temp,THBaseService.java,6309,6321,temp,Hbase.java,7243,7256
//,3
public class xxx {
          public void onComplete(java.util.List<TTableDescriptor> o) {
            getTableDescriptorsByPattern_result result = new getTableDescriptorsByPattern_result();
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