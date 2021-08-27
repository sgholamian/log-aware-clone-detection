//,temp,DurableSubscriptionOffline2Test.java,57,61,temp,DurableSubscriptionOffline4Test.java,55,60
//,3
public class xxx {
    public DurableSubscriptionOffline2Test(Boolean keepDurableSubsActive) {
        this.keepDurableSubsActive = keepDurableSubsActive.booleanValue();

        LOG.info(">>>> running {} with keepDurableSubsActive: {}", testName.getMethodName(), this.keepDurableSubsActive);
    }

};