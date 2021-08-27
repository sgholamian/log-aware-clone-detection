//,temp,IAM2Producer.java,556,594,temp,SecretsManagerProducer.java,337,372
//,3
public class xxx {
    private void replicateSecretToRegions(SecretsManagerClient secretsManagerClient, Exchange exchange)
            throws InvalidPayloadException {
        ReplicateSecretToRegionsRequest request = null;
        ReplicateSecretToRegionsResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(ReplicateSecretToRegionsRequest.class);
        } else {
            ReplicateSecretToRegionsRequest.Builder builder = ReplicateSecretToRegionsRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(SecretsManagerConstants.SECRET_ID))) {
                String secretId = exchange.getIn().getHeader(SecretsManagerConstants.SECRET_ID, String.class);
                builder.secretId(secretId);
            } else {
                throw new IllegalArgumentException("Secret Id must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(SecretsManagerConstants.SECRET_REPLICATION_REGIONS))) {
                String regions = exchange.getIn().getHeader(SecretsManagerConstants.SECRET_REPLICATION_REGIONS, String.class);
                String[] s = regions.split(",");
                for (String region : s) {
                    ReplicaRegionType.Builder regionType = ReplicaRegionType.builder();
                    regionType.region(region);
                    builder.addReplicaRegions(regionType.build());
                }
            } else {
                throw new IllegalArgumentException("Replica Regions must be specified");
            }
            request = builder.build();
        }
        try {
            result = secretsManagerClient.replicateSecretToRegions(request);
        } catch (AwsServiceException ase) {
            LOG.trace("Replicate Secret to region command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};