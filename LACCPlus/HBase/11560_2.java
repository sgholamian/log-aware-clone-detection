//,temp,THBaseService.java,6645,6672,temp,Hbase.java,5612,5647
//,3
public class xxx {
          public void onError(java.lang.Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TSerializable msg;
            createTable_result result = new createTable_result();
            if (e instanceof IOError) {
              result.io = (IOError) e;
              result.setIoIsSet(true);
              msg = result;
            } else if (e instanceof IllegalArgument) {
              result.ia = (IllegalArgument) e;
              result.setIaIsSet(true);
              msg = result;
            } else if (e instanceof AlreadyExists) {
              result.exist = (AlreadyExists) e;
              result.setExistIsSet(true);
              msg = result;
            } else if (e instanceof org.apache.thrift.transport.TTransportException) {
              _LOGGER.error("TTransportException inside handler", e);
              fb.close();
              return;
            } else if (e instanceof org.apache.thrift.TApplicationException) {
              _LOGGER.error("TApplicationException inside handler", e);
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = (org.apache.thrift.TApplicationException)e;
            } else {
              _LOGGER.error("Exception inside handler", e);
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
            }
            try {
              fcall.sendResponse(fb,msg,msgType,seqid);
            } catch (java.lang.Exception ex) {
              _LOGGER.error("Exception writing to internal frame buffer", ex);
              fb.close();
            }
          }

};