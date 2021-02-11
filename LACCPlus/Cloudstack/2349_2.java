//,temp,CloudStackPrimaryDataStoreDriverImpl.java,127,143,temp,HypervisorHelperImpl.java,95,115
//,3
public class xxx {
    @Override
    public boolean forgetObject(DataTO object, Scope scope, Long storeId) {
        EndPoint ep = selector.select(scope, storeId);
        ForgetObjectCmd cmd = new ForgetObjectCmd(object);
        Answer answer = null;
        if (ep == null) {
            String errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
            s_logger.error(errMsg);
            answer = new Answer(cmd, false, errMsg);
        } else {
            answer = ep.sendMessage(cmd);
        }
        if (answer == null || !answer.getResult()) {
            String errMsg = answer == null ? null : answer.getDetails();
            if (errMsg != null) {
                s_logger.debug("Failed to forget object: " + errMsg);
            }
            return false;
        }
        return true;
    }

};