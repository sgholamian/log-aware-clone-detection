//,temp,MSK2Producer.java,183,215,temp,KMS2Producer.java,281,313
//,2
public class xxx {
    private void deleteCluster(KafkaClient mskClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DeleteClusterRequest) {
                DeleteClusterResponse result;
                try {
                    result = mskClient.deleteCluster((DeleteClusterRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Delete Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DeleteClusterRequest.Builder builder = DeleteClusterRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(MSK2Constants.CLUSTER_ARN))) {
                String arn = exchange.getIn().getHeader(MSK2Constants.CLUSTER_ARN, String.class);
                builder.clusterArn(arn);
            } else {
                throw new IllegalArgumentException("Cluster ARN must be specified");
            }
            DeleteClusterResponse result;
            try {
                result = mskClient.deleteCluster(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Delete Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};