//,temp,ThriftHiveMetastore.java,43640,43667,temp,ThriftHiveMetastore.java,41195,41234
//,3
public class xxx {
          public void onError(java.lang.Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TSerializable msg;
            create_or_update_wm_mapping_result result = new create_or_update_wm_mapping_result();
            if (e instanceof AlreadyExistsException) {
              result.o1 = (AlreadyExistsException) e;
              result.setO1IsSet(true);
              msg = result;
            } else if (e instanceof NoSuchObjectException) {
              result.o2 = (NoSuchObjectException) e;
              result.setO2IsSet(true);
              msg = result;
            } else if (e instanceof InvalidObjectException) {
              result.o3 = (InvalidObjectException) e;
              result.setO3IsSet(true);
              msg = result;
            } else if (e instanceof MetaException) {
              result.o4 = (MetaException) e;
              result.setO4IsSet(true);
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