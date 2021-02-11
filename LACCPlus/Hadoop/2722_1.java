//,temp,TestKMS.java,1732,1747,temp,TestKMS.java,1717,1729
//,3
public class xxx {
          @Override
          public Void run() throws Exception {
            KeyProvider kp = createProvider(uri, conf);
            try {
              KeyProviderCryptoExtension kpCE = KeyProviderCryptoExtension.
                  createKeyProviderCryptoExtension(kp);
              List<EncryptedKeyVersion> ekvs = new ArrayList<>(2);
              ekvs.add(encKv);
              ekvs.add(encKv);
              kpCE.reencryptEncryptedKeys(ekvs);
              fail("Should not have been able to reencryptEncryptedKeys");
            } catch (AuthorizationException ex) {
              LOG.info("reencryptEncryptedKeys caught expected exception.", ex);
            }
            return null;
          }

};