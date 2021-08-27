//,temp,sample_8496.java,2,16,temp,sample_6349.java,2,12
//,3
public class xxx {
public SecureRandom createSecureRandom() throws GeneralSecurityException {
SecureRandom secureRandom;
if (this.getProvider() != null) {
secureRandom = SecureRandom.getInstance(this.parsePropertyValue(this.getAlgorithm()), this.parsePropertyValue(this.getProvider()));
} else {
secureRandom = SecureRandom.getInstance(this.parsePropertyValue(this.getAlgorithm()));
}


log.info("securerandom is using provider and algorithm");
}

};