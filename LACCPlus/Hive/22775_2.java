//,temp,ThriftHiveMetastore.java,41687,41739,temp,ThriftHiveMetastore.java,33768,33815
//,3
public class xxx {
          public void onError(java.lang.Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TSerializable msg;
            isPartitionMarkedForEvent_result result = new isPartitionMarkedForEvent_result();
            if (e instanceof MetaException) {
              result.o1 = (MetaException) e;
              result.setO1IsSet(true);
              msg = result;
            } else if (e instanceof NoSuchObjectException) {
              result.o2 = (NoSuchObjectException) e;
              result.setO2IsSet(true);
              msg = result;
            } else if (e instanceof UnknownDBException) {
              result.o3 = (UnknownDBException) e;
              result.setO3IsSet(true);
              msg = result;
            } else if (e instanceof UnknownTableException) {
              result.o4 = (UnknownTableException) e;
              result.setO4IsSet(true);
              msg = result;
            } else if (e instanceof UnknownPartitionException) {
              result.o5 = (UnknownPartitionException) e;
              result.setO5IsSet(true);
              msg = result;
            } else if (e instanceof InvalidPartitionException) {
              result.o6 = (InvalidPartitionException) e;
              result.setO6IsSet(true);
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