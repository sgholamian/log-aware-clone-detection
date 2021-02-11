//,temp,HypervisorHelperImpl.java,75,93,temp,CloudStackImageStoreDriverImpl.java,118,140
//,3
public class xxx {
    @Override
    public DataTO introduceObject(DataTO object, Scope scope, Long storeId) {
        EndPoint ep = selector.select(scope, storeId);
        IntroduceObjectCmd cmd = new IntroduceObjectCmd(object);
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
            throw new CloudRuntimeException("Failed to introduce object, due to " + errMsg);
        }
        IntroduceObjectAnswer introduceObjectAnswer = (IntroduceObjectAnswer)answer;
        return introduceObjectAnswer.getDataTO();
    }

};