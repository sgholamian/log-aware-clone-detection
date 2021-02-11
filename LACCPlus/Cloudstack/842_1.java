//,temp,RootCAProvider.java,227,235,temp,RootCAProvider.java,217,225
//,3
public class xxx {
    @Override
    public Certificate issueCertificate(final String csr, final List<String> domainNames, final List<String> ipAddresses, final int validityDays) {
        try {
            return generateCertificateUsingCsr(csr, domainNames, ipAddresses, validityDays);
        } catch (final CertificateException | IOException | SignatureException | NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | OperatorCreationException e) {
            LOG.error("Failed to generate certificate from CSR: ", e);
            throw new CloudRuntimeException("Failed to generate certificate using CSR due to:" + e.getMessage());
        }
    }

};