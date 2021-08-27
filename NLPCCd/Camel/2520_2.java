//,temp,sample_321.java,2,14,temp,sample_851.java,2,14
//,2
public class xxx {
public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
for (Callback callback : callbacks) {
if (callback instanceof PasswordCallback) {
PasswordCallback pc = (PasswordCallback) callback;
pc.setPassword(principal.getPassword().toCharArray());
} else if (callback instanceof NameCallback) {
NameCallback nc = (NameCallback) callback;


log.info("setting username on callback");
}
}
}

};