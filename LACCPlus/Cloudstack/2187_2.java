//,temp,VmwareResource.java,5108,5115,temp,VmwareResource.java,5100,5106
//,3
public class xxx {
    protected Answer execute(CheckOnHostCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource CheckOnHostCommand: " + _gson.toJson(cmd));
        }

        return new CheckOnHostAnswer(cmd, null, "Not Implmeneted");
    }

};