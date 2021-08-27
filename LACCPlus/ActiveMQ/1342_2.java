//,temp,DurableSubscriptionOffline2Test.java,57,61,temp,DurableSubscriptionOffline4Test.java,55,60
//,3
public class xxx {
    public DurableSubscriptionOffline4Test(Boolean keepDurableSubsActive) {
        this.journalMaxFileLength = 64 * 1024;
        this.keepDurableSubsActive = keepDurableSubsActive.booleanValue();

        LOG.info(">>>> running {} with keepDurableSubsActive: {}, journalMaxFileLength", testName.getMethodName(), this.keepDurableSubsActive, journalMaxFileLength);
    }

};