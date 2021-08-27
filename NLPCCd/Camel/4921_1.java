//,temp,sample_2832.java,2,17,temp,sample_2833.java,2,18
//,3
public class xxx {
public void preWriteCheck() throws Exception {
boolean noop = false;
if (loggedIn) {
if (getEndpoint().getConfiguration().isSendNoop()) {
try {
noop = getOperations().sendNoop();
} catch (Exception e) {
noop = false;
loggedIn = false;
}


log.info("prewritecheck send noop success");
}
}
}

};