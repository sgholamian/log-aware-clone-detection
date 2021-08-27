//,temp,KMS2Producer.java,209,245,temp,EKS2Producer.java,129,167
//,3
public class xxx {
    private void createCluster(EksClient eksClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof CreateClusterRequest) {
                CreateClusterResponse result;
                try {
                    result = eksClient.createCluster((CreateClusterRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Create Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            CreateClusterRequest.Builder builder = CreateClusterRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EKS2Constants.CLUSTER_NAME))) {
                String name = exchange.getIn().getHeader(EKS2Constants.CLUSTER_NAME, String.class);
                builder.name(name);
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EKS2Constants.ROLE_ARN))) {
                String roleArn = exchange.getIn().getHeader(EKS2Constants.ROLE_ARN, String.class);
                builder.roleArn(roleArn);
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EKS2Constants.VPC_CONFIG))) {
                VpcConfigRequest vpcConfig = exchange.getIn().getHeader(EKS2Constants.VPC_CONFIG, VpcConfigRequest.class);
                builder.resourcesVpcConfig(vpcConfig);
            }
            CreateClusterResponse result;
            try {
                result = eksClient.createCluster(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Create Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};