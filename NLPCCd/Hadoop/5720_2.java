//,temp,sample_8906.java,2,11,temp,sample_8909.java,2,19
//,3
public class xxx {
public void dummy_method(){
boolean valid = false;
if (JWSObject.State.SIGNED == jwtToken.getState()) {
if (jwtToken.getSignature() != null) {
try {
JWSVerifier verifier = new RSASSAVerifier(publicKey);
if (jwtToken.verify(verifier)) {
valid = true;
} else {
}
} catch (JOSEException je) {


log.info("error while validating signature");
}
}
}
}

};