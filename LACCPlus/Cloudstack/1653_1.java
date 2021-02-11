//,temp,SolidFireSharedPrimaryDataStoreLifeCycle.java,653,666,temp,UserVmManagerImpl.java,6497,6510
//,3
public class xxx {
    private void sendModifyTargetsCommand(ModifyTargetsCommand cmd, long hostId) {
        Answer answer = agentMgr.easySend(hostId, cmd);

        if (answer == null) {
            String msg = "Unable to get an answer to the modify targets command";

            LOGGER.warn(msg);
        }
        else if (!answer.getResult()) {
            String msg = "Unable to modify target on the following host: " + hostId;

            LOGGER.warn(msg);
        }
    }

};