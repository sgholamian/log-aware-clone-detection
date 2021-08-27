//,temp,KubernetesReplicationControllersProducer.java,202,225,temp,KubernetesHPAProducer.java,161,177
//,3
public class xxx {
    protected void doScaleReplicationController(Exchange exchange, String operation) throws Exception {
        String rcName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        Integer replicasNumber
                = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_REPLICAS, Integer.class);
        if (ObjectHelper.isEmpty(rcName)) {
            LOG.error("Scale a specific replication controller require specify a replication controller name");
            throw new IllegalArgumentException(
                    "Scale a specific replication controller require specify a replication controller name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Scale a specific replication controller require specify a namespace name");
            throw new IllegalArgumentException("Scale a specific replication controller require specify a namespace name");
        }
        if (ObjectHelper.isEmpty(replicasNumber)) {
            LOG.error("Scale a specific replication controller require specify a replicas number");
            throw new IllegalArgumentException("Scale a specific replication controller require specify a replicas number");
        }
        ReplicationController rcScaled = getEndpoint().getKubernetesClient().replicationControllers().inNamespace(namespaceName)
                .withName(rcName).scale(replicasNumber, false);

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(rcScaled.getStatus().getReplicas());
    }

};