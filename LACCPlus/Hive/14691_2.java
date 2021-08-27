//,temp,ThriftHiveMetastore.java,40461,40496,temp,ThriftHiveMetastore.java,38506,38541
//,2
public class xxx {
          public void onError(java.lang.Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TSerializable msg;
            heartbeat_result result = new heartbeat_result();
            if (e instanceof NoSuchLockException) {
              result.o1 = (NoSuchLockException) e;
              result.setO1IsSet(true);
              msg = result;
            } else if (e instanceof NoSuchTxnException) {
              result.o2 = (NoSuchTxnException) e;
              result.setO2IsSet(true);
              msg = result;
            } else if (e instanceof TxnAbortedException) {
              result.o3 = (TxnAbortedException) e;
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