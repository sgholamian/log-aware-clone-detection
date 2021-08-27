//,temp,KMS2Producer.java,175,207,temp,EKS2Producer.java,203,235
//,2
public class xxx {
    private void deleteCluster(EksClient eksClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DeleteClusterRequest) {
                DeleteClusterResponse result;
                try {
                    result = eksClient.deleteCluster((DeleteClusterRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Delete Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DeleteClusterRequest.Builder builder = DeleteClusterRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EKS2Constants.CLUSTER_NAME))) {
                String name = exchange.getIn().getHeader(EKS2Constants.CLUSTER_NAME, String.class);
                builder.name(name);
            } else {
                throw new IllegalArgumentException("Cluster name must be specified");
            }
            DeleteClusterResponse result;
            try {
                result = eksClient.deleteCluster(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Delete Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};