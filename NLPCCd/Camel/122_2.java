//,temp,sample_7570.java,2,9,temp,sample_850.java,2,11
//,3
public class xxx {
public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
for (Callback callback : callbacks) {
if (callback instanceof PasswordCallback) {
PasswordCallback pc = (PasswordCallback) callback;


log.info("setting password on callback");
}
}
}

};