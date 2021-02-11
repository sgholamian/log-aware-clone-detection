//,temp,VmwareResource.java,5108,5115,temp,VmwareResource.java,5048,5054
//,3
public class xxx {
    protected Answer execute(MaintainCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource MaintainCommand: " + _gson.toJson(cmd));
        }

        return new MaintainAnswer(cmd, "Put host in maintaince");
    }

};