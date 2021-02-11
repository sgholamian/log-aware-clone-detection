//,temp,sample_3714.java,2,10,temp,sample_2407.java,2,10
//,2
public class xxx {
public void processEvent(ObjectInDataStoreStateMachine.Event event) {
try {
objectInStoreMgr.update(this, event);
} catch (Exception e) {


log.info("failed to update state");
}
}

};