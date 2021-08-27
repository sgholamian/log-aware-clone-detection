//,temp,IAM2Producer.java,373,415,temp,MSK2Producer.java,130,181
//,3
public class xxx {
    private void createCluster(KafkaClient mskClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof CreateClusterRequest) {
                CreateClusterResponse response;
                try {
                    response = mskClient.createCluster((CreateClusterRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Create Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(response);
            }
        } else {
            CreateClusterRequest.Builder builder = CreateClusterRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(MSK2Constants.CLUSTER_NAME))) {
                String name = exchange.getIn().getHeader(MSK2Constants.CLUSTER_NAME, String.class);
                builder.clusterName(name);
            } else {
                throw new IllegalArgumentException("Cluster Name must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(MSK2Constants.CLUSTER_KAFKA_VERSION))) {
                String version = exchange.getIn().getHeader(MSK2Constants.CLUSTER_KAFKA_VERSION, String.class);
                builder.kafkaVersion(version);
            } else {
                throw new IllegalArgumentException("Kafka Version must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(MSK2Constants.BROKER_NODES_NUMBER))) {
                Integer nodesNumber = exchange.getIn().getHeader(MSK2Constants.BROKER_NODES_NUMBER, Integer.class);
                builder.numberOfBrokerNodes(nodesNumber);
            } else {
                throw new IllegalArgumentException("Kafka Version must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(MSK2Constants.BROKER_NODES_GROUP_INFO))) {
                BrokerNodeGroupInfo brokerNodesGroupInfo
                        = exchange.getIn().getHeader(MSK2Constants.BROKER_NODES_GROUP_INFO, BrokerNodeGroupInfo.class);
                builder.brokerNodeGroupInfo(brokerNodesGroupInfo);
            } else {
                throw new IllegalArgumentException("BrokerNodeGroupInfo must be specified");
            }
            CreateClusterResponse response;
            try {
                response = mskClient.createCluster(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Create Cluster command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(response);
        }
    }

};