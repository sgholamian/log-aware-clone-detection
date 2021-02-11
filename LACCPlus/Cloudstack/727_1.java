//,temp,Ovm3StorageProcessor.java,799,803,temp,Ovm3StorageProcessor.java,791,795
//,2
public class xxx {
    @Override
    public Answer forgetObject(ForgetObjectCmd cmd) {
        LOGGER.debug("execute forgetObject: "+ cmd.getClass());
        return new Answer(cmd, false, "not implemented yet");
    }

};