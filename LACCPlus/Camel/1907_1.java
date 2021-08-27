//,temp,MSK2Producer.java,217,249,temp,KMS2Producer.java,143,173
//,3
public class xxx {
    private void describeCluster(KafkaClient mskClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DescribeClusterRequest) {
                DescribeClusterResponse result;
                try {
                    result = mskClient.describeCluster((DescribeClusterRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Delete Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DescribeClusterRequest.Builder builder = DescribeClusterRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(MSK2Constants.CLUSTER_ARN))) {
                String arn = exchange.getIn().getHeader(MSK2Constants.CLUSTER_ARN, String.class);
                builder.clusterArn(arn);
            } else {
                throw new IllegalArgumentException("Cluster ARN must be specified");
            }
            DescribeClusterResponse result;
            try {
                result = mskClient.describeCluster(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Delete Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};