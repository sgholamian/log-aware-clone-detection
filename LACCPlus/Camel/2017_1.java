//,temp,ECS2Producer.java,166,198,temp,ECS2Producer.java,98,130
//,3
public class xxx {
    private void describeCluster(EcsClient ecsClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DescribeClustersRequest) {
                DescribeClustersResponse result;
                try {
                    DescribeClustersRequest request = (DescribeClustersRequest) payload;
                    result = ecsClient.describeClusters(request);
                } catch (AwsServiceException ase) {
                    LOG.trace("Describe Clusters command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DescribeClustersRequest.Builder builder = DescribeClustersRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(ECS2Constants.CLUSTER_NAME))) {
                String clusterName = exchange.getIn().getHeader(ECS2Constants.CLUSTER_NAME, String.class);
                builder.clusters(clusterName);
            }
            DescribeClustersResponse result;
            try {
                DescribeClustersRequest request = builder.build();
                result = ecsClient.describeClusters(request);
            } catch (AwsServiceException ase) {
                LOG.trace("Describe Clusters command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};