//,temp,sample_8906.java,2,11,temp,sample_8909.java,2,19
//,3
public class xxx {
protected boolean validateSignature(SignedJWT jwtToken) {
boolean valid = false;
if (JWSObject.State.SIGNED == jwtToken.getState()) {
if (jwtToken.getSignature() != null) {


log.info("jwt token signature is not null");
}
}
}

};