//,temp,ThriftHiveMetastore.java,35008,35047,temp,ThriftHiveMetastore.java,34415,34454
//,2
public class xxx {
          public void onError(java.lang.Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TSerializable msg;
            set_aggr_stats_for_result result = new set_aggr_stats_for_result();
            if (e instanceof NoSuchObjectException) {
              result.o1 = (NoSuchObjectException) e;
              result.setO1IsSet(true);
              msg = result;
            } else if (e instanceof InvalidObjectException) {
              result.o2 = (InvalidObjectException) e;
              result.setO2IsSet(true);
              msg = result;
            } else if (e instanceof MetaException) {
              result.o3 = (MetaException) e;
              result.setO3IsSet(true);
              msg = result;
            } else if (e instanceof InvalidInputException) {
              result.o4 = (InvalidInputException) e;
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