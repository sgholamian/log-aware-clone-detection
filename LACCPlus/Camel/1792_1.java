//,temp,KubernetesReplicationControllersProducer.java,153,181,temp,KubernetesHPAProducer.java,133,159
//,3
public class xxx {
    protected void doCreateReplicationController(Exchange exchange, String operation) throws Exception {
        ReplicationController rc = null;
        String rcName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        ReplicationControllerSpec rcSpec = exchange.getIn()
                .getHeader(KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_SPEC, ReplicationControllerSpec.class);
        if (ObjectHelper.isEmpty(rcName)) {
            LOG.error("Create a specific replication controller require specify a replication controller name");
            throw new IllegalArgumentException(
                    "Create a specific replication controller require specify a replication controller name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Create a specific replication controller require specify a namespace name");
            throw new IllegalArgumentException("Create a specific replication controller require specify a namespace name");
        }
        if (ObjectHelper.isEmpty(rcSpec)) {
            LOG.error("Create a specific replication controller require specify a replication controller spec bean");
            throw new IllegalArgumentException(
                    "Create a specific replication controller require specify a replication controller spec bean");
        }
        Map<String, String> labels
                = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLERS_LABELS, Map.class);
        ReplicationController rcCreating = new ReplicationControllerBuilder().withNewMetadata().withName(rcName)
                .withLabels(labels).endMetadata().withSpec(rcSpec).build();
        rc = getEndpoint().getKubernetesClient().replicationControllers().inNamespace(namespaceName).create(rcCreating);

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(rc);
    }

};