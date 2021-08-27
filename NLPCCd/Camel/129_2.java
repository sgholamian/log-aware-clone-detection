//,temp,sample_3733.java,2,15,temp,sample_2405.java,2,18
//,3
public class xxx {
public void dummy_method(){
try {
if (store != null && store.isConnected()) {
connected = true;
}
} catch (Exception e) {
}
if (!connected) {
store = null;
folder = null;
if (LOG.isDebugEnabled()) {


log.info("connecting to mailstore");
}
}
}

};