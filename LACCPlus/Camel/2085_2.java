//,temp,AWS2EC2Producer.java,230,266,temp,ECS2Producer.java,132,164
//,3
public class xxx {
    private void createCluster(EcsClient ecsClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof CreateClusterRequest) {
                CreateClusterResponse result;
                try {
                    CreateClusterRequest request = (CreateClusterRequest) payload;
                    result = ecsClient.createCluster(request);
                } catch (AwsServiceException ase) {
                    LOG.trace("Create Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            CreateClusterRequest.Builder builder = CreateClusterRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(ECS2Constants.CLUSTER_NAME))) {
                String name = exchange.getIn().getHeader(ECS2Constants.CLUSTER_NAME, String.class);
                builder.clusterName(name);
            }
            CreateClusterResponse result;
            try {
                CreateClusterRequest request = builder.build();
                result = ecsClient.createCluster(request);
            } catch (AwsServiceException ase) {
                LOG.trace("Create Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};