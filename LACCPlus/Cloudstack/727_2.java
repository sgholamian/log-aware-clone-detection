//,temp,Ovm3StorageProcessor.java,799,803,temp,Ovm3StorageProcessor.java,791,795
//,2
public class xxx {
    @Override
    public Answer introduceObject(IntroduceObjectCmd cmd) {
        LOGGER.debug("execute introduceObject: "+ cmd.getClass());
        return new Answer(cmd, false, "not implemented yet");
    }

};