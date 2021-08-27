//,temp,ECS2Producer.java,200,234,temp,MQ2Producer.java,258,291
//,3
public class xxx {
    private void deleteCluster(EcsClient ecsClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DeleteClusterRequest) {
                DeleteClusterResponse result;
                try {
                    DeleteClusterRequest request = (DeleteClusterRequest) payload;
                    result = ecsClient.deleteCluster(request);
                } catch (AwsServiceException ase) {
                    LOG.trace("Delete Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DeleteClusterRequest.Builder builder = DeleteClusterRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(ECS2Constants.CLUSTER_NAME))) {
                String name = exchange.getIn().getHeader(ECS2Constants.CLUSTER_NAME, String.class);
                builder.cluster(name);
            } else {
                throw new IllegalArgumentException("Cluster name must be specified");
            }
            DeleteClusterResponse result;
            try {
                DeleteClusterRequest request = builder.build();
                result = ecsClient.deleteCluster(request);
            } catch (AwsServiceException ase) {
                LOG.trace("Delete Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};