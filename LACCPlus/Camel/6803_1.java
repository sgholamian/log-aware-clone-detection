//,temp,KeyManagersParameters.java,76,110,temp,TrustManagersParameters.java,73,107
//,3
public class xxx {
    public KeyManager[] createKeyManagers() throws GeneralSecurityException, IOException {

        LOG.trace("Creating KeyManager[] from KeyManagersParameters [{}].", this);

        KeyManager[] keyManagers;

        String kmfAlgorithm = this.parsePropertyValue(this.getAlgorithm());
        if (kmfAlgorithm == null) {
            kmfAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
        }

        KeyManagerFactory kmf;
        if (this.getProvider() == null) {
            kmf = KeyManagerFactory.getInstance(kmfAlgorithm);
        } else {
            kmf = KeyManagerFactory.getInstance(kmfAlgorithm, this.parsePropertyValue(this.getProvider()));
        }

        LOG.debug("KeyManagerFactory [{}], initialized from [{}], is using provider [{}] and algorithm [{}].",
                new Object[] { kmf, this, kmf.getProvider(), kmf.getAlgorithm() });

        char[] kmfPassword = null;
        if (this.getKeyPassword() != null) {
            kmfPassword = this.parsePropertyValue(this.getKeyPassword()).toCharArray();
        }

        KeyStore ks = this.getKeyStore() == null ? null : this.getKeyStore().createKeyStore();

        kmf.init(ks, kmfPassword);
        keyManagers = kmf.getKeyManagers();

        LOG.debug("KeyManager[] [{}], initialized from KeyManagerFactory [{}].", keyManagers, kmf);

        return keyManagers;
    }

};