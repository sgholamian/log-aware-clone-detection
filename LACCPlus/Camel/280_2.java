//,temp,KMS2Producer.java,247,279,temp,EKS2Producer.java,169,201
//,2
public class xxx {
    private void describeCluster(EksClient eksClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DescribeClusterRequest) {
                DescribeClusterResponse result;
                try {
                    result = eksClient.describeCluster((DescribeClusterRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Describe Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DescribeClusterRequest.Builder builder = DescribeClusterRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EKS2Constants.CLUSTER_NAME))) {
                String name = exchange.getIn().getHeader(EKS2Constants.CLUSTER_NAME, String.class);
                builder.name(name);
            } else {
                throw new IllegalArgumentException("Cluster name must be specified");
            }
            DescribeClusterResponse result;
            try {
                result = eksClient.describeCluster(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Describe Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};