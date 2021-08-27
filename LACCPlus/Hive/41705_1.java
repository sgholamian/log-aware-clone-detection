//,temp,LlapTaskCommunicator.java,402,411,temp,LlapTaskSchedulerService.java,170,177
//,3
public class xxx {
            @Override
            public void indicateError(Throwable t) {
              LOG.info("Error registering dag with"
                  + " appId=" + currentQueryIdentifierProto.getApplicationIdString()
                  + " dagId=" + currentQueryIdentifierProto.getDagIndex()
                  + " to node " + node.getHost());
              if (!processSendError(t)) {
                callback.setError(null, t);
              }
            }

};