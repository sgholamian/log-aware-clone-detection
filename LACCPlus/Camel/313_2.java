//,temp,IAM2Producer.java,303,333,temp,MSK2Producer.java,98,128
//,2
public class xxx {
    private void listClusters(KafkaClient mskClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof ListClustersRequest) {
                ListClustersResponse result;
                try {
                    result = mskClient.listClusters((ListClustersRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("List Clusters command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            ListClustersRequest.Builder builder = ListClustersRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(MSK2Constants.CLUSTERS_FILTER))) {
                String filter = exchange.getIn().getHeader(MSK2Constants.CLUSTERS_FILTER, String.class);
                builder.clusterNameFilter(filter);
            }
            ListClustersResponse result;
            try {
                result = mskClient.listClusters(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("List Clusters command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};