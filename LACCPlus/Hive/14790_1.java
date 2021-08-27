//,temp,ThriftHiveMetastore.java,41122,41157,temp,ThriftHiveMetastore.java,40603,40638
//,2
public class xxx {
          public void onError(java.lang.Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TSerializable msg;
            drop_wm_pool_result result = new drop_wm_pool_result();
            if (e instanceof NoSuchObjectException) {
              result.o1 = (NoSuchObjectException) e;
              result.setO1IsSet(true);
              msg = result;
            } else if (e instanceof InvalidOperationException) {
              result.o2 = (InvalidOperationException) e;
              result.setO2IsSet(true);
              msg = result;
            } else if (e instanceof MetaException) {
              result.o3 = (MetaException) e;
              result.setO3IsSet(true);
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