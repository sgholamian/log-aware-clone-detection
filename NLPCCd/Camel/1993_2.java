//,temp,sample_5865.java,2,14,temp,sample_5866.java,2,15
//,2
public class xxx {
private synchronized void initEncryptor() {
if (encryptor == null) {
ObjectHelper.notEmpty("password", password);
StandardPBEStringEncryptor pbeStringEncryptor = new StandardPBEStringEncryptor();
pbeStringEncryptor.setPassword(password);
if (algorithm != null) {
pbeStringEncryptor.setAlgorithm(algorithm);
} else {


log.info("initialized encryptor using default algorithm and provided password");
}
}
}

};