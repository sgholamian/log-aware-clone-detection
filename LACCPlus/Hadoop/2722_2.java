//,temp,TestKMS.java,1732,1747,temp,TestKMS.java,1717,1729
//,3
public class xxx {
          @Override
          public Void run() throws Exception {
            KeyProvider kp = createProvider(uri, conf);
            try {
              KeyProviderCryptoExtension kpCE = KeyProviderCryptoExtension.
                  createKeyProviderCryptoExtension(kp);
              kpCE.reencryptEncryptedKey(encKv);
              fail("Should not have been able to reencryptEncryptedKey");
            } catch (AuthorizationException ex) {
              LOG.info("reencryptEncryptedKey caught expected exception.", ex);
            }
            return null;
          }

};