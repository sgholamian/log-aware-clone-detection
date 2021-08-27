//,temp,KeyManagersParameters.java,76,110,temp,TrustManagersParameters.java,73,107
//,3
public class xxx {
    public TrustManager[] createTrustManagers() throws GeneralSecurityException, IOException {
        if (trustManager != null) {
            // use existing trust manager
            return new TrustManager[] { trustManager };
        }

        LOG.trace("Creating TrustManager[] from TrustManagersParameters [{}]", this);

        TrustManager[] trustManagers = null;

        if (this.getKeyStore() != null) {
            String tmfAlgorithm = this.parsePropertyValue(this.getAlgorithm());
            if (tmfAlgorithm == null) {
                tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            }

            TrustManagerFactory tmf;
            if (this.getProvider() == null) {
                tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            } else {
                tmf = TrustManagerFactory.getInstance(tmfAlgorithm, this.parsePropertyValue(this.getProvider()));
            }

            LOG.debug("TrustManagerFactory [{}] is using provider [{}] and algorithm [{}].",
                    new Object[] { tmf, tmf.getProvider(), tmf.getAlgorithm() });

            KeyStore ks = this.getKeyStore() == null ? null : this.getKeyStore().createKeyStore();
            tmf.init(ks);
            trustManagers = tmf.getTrustManagers();

            LOG.debug("TrustManager[] [{}], initialized from TrustManagerFactory [{}].", trustManagers, tmf);
        }

        return trustManagers;
    }

};