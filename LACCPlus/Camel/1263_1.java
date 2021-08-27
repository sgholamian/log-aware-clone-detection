//,temp,PGPDataFormatUtil.java,330,346,temp,PGPDataFormatUtil.java,221,238
//,3
public class xxx {
    private static boolean isSigningKey(PGPSecretKey secKey) {
        if (!secKey.isSigningKey()) {
            return false;
        }
        Boolean hasSigningKeyFlag = hasOneOfExpectedKeyFlags(secKey.getPublicKey(), new int[] { KeyFlags.SIGN_DATA });
        if (hasSigningKeyFlag != null && !hasSigningKeyFlag) {
            // not a signing key --> ignore
            LOG.debug(
                    "Secret key with key ID {} found for specified user ID part. But this key will not be used for signing because of its key flags.",
                    Long.toString(secKey.getKeyID()));
            return false;
        } else {
            // also if there are not any keyflags (hasSigningKeyFlag=null),  true is returned!
            return true;
        }

    }

};