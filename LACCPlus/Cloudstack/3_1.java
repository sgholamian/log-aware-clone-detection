//,temp,VmwareResource.java,5108,5115,temp,VmwareResource.java,844,851
//,3
public class xxx {
    protected Answer execute(ModifySshKeysCommand cmd) {
        //do not log the command contents for this command. do NOT log the ssh keys
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource ModifySshKeysCommand.");
        }

        return new Answer(cmd);
    }

};