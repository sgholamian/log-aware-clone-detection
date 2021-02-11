//,temp,RootCAProvider.java,227,235,temp,RootCAProvider.java,217,225
//,3
public class xxx {
    @Override
    public Certificate issueCertificate(final List<String> domainNames, final List<String> ipAddresses, final int validityDays) {
        try {
            return generateCertificate(domainNames, ipAddresses, validityDays);
        } catch (final CertificateException | IOException | SignatureException | NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | OperatorCreationException e) {
            LOG.error("Failed to create client certificate, due to: ", e);
            throw new CloudRuntimeException("Failed to generate certificate due to:" + e.getMessage());
        }
    }

};