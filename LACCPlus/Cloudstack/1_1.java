//,temp,VmwareResource.java,5040,5046,temp,VmwareResource.java,844,851
//,3
public class xxx {
    protected Answer execute(SetupCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource SetupCommand: " + _gson.toJson(cmd));
        }

        return new SetupAnswer(cmd, false);
    }

};