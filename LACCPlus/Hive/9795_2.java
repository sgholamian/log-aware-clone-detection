//,temp,ThriftHiveMetastore.java,38890,38917,temp,ThriftHiveMetastore.java,30415,30450
//,3
public class xxx {
          public void onError(java.lang.Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TSerializable msg;
            add_partitions_pspec_result result = new add_partitions_pspec_result();
            if (e instanceof InvalidObjectException) {
              result.o1 = (InvalidObjectException) e;
              result.setO1IsSet(true);
              msg = result;
            } else if (e instanceof AlreadyExistsException) {
              result.o2 = (AlreadyExistsException) e;
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