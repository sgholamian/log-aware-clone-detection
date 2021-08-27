//,temp,ECS2Producer.java,166,198,temp,ECS2Producer.java,98,130
//,3
public class xxx {
    private void listClusters(EcsClient ecsClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof ListClustersRequest) {
                ListClustersResponse result;
                try {
                    ListClustersRequest request = (ListClustersRequest) payload;
                    result = ecsClient.listClusters(request);
                } catch (AwsServiceException ase) {
                    LOG.trace("List Clusters command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            Builder builder = ListClustersRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(ECS2Constants.MAX_RESULTS))) {
                int maxRes = exchange.getIn().getHeader(ECS2Constants.MAX_RESULTS, Integer.class);
                builder.maxResults(maxRes);
            }
            ListClustersResponse result;
            try {
                ListClustersRequest request = builder.build();
                result = ecsClient.listClusters(request);
            } catch (AwsServiceException ase) {
                LOG.trace("List Clusters command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};