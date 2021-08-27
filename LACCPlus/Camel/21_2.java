//,temp,Athena2Producer.java,407,416,temp,Athena2Producer.java,382,391
//,2
public class xxx {
    private String determineKmsKey(final Exchange exchange) {
        String kmsKey = exchange.getIn().getHeader(Athena2Constants.KMS_KEY, String.class);

        if (ObjectHelper.isEmpty(kmsKey)) {
            kmsKey = getConfiguration().getKmsKey();
            LOG.trace("AWS Athena kms key is missing, using default one [{}]", kmsKey);
        }

        return kmsKey;
    }

};