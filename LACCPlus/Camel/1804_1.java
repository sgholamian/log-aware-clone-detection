//,temp,EKS2Producer.java,97,127,temp,EventbridgeProducer.java,319,350
//,3
public class xxx {
    private void listClusters(EksClient eksClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof ListClustersRequest) {
                ListClustersResponse result;
                try {
                    result = eksClient.listClusters((ListClustersRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("List Clusters command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            ListClustersRequest.Builder builder = ListClustersRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EKS2Constants.MAX_RESULTS))) {
                int maxRes = exchange.getIn().getHeader(EKS2Constants.MAX_RESULTS, Integer.class);
                builder.maxResults(maxRes);
            }
            ListClustersResponse result;
            try {
                result = eksClient.listClusters(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("List Clusters command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};