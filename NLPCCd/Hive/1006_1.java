//,temp,sample_3180.java,2,18,temp,sample_3179.java,2,11
//,3
public class xxx {
public void dummy_method(){
if (!doesExist && event.getResultCode() != KeeperException.Code.OK.intValue()) {
if (LOG.isInfoEnabled()) {
}
startCreateCurrentNode();
return;
}
State localState = state.get();
switch (localState) {
case CLOSED: case LATENT: return;
case INITIAL_SELECTION: if (doesExist) {


log.info("slot was occupied");
}
}
}

};