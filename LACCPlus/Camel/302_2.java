//,temp,KubernetesResourcesQuotaProducer.java,120,136,temp,KubernetesReplicationControllersProducer.java,134,151
//,2
public class xxx {
    protected void doGetReplicationController(Exchange exchange, String operation) throws Exception {
        ReplicationController rc = null;
        String rcName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(rcName)) {
            LOG.error("Get a specific replication controller require specify a replication controller name");
            throw new IllegalArgumentException(
                    "Get a specific replication controller require specify a replication controller name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Get a specific replication controller require specify a namespace name");
            throw new IllegalArgumentException("Get a specific replication controller require specify a namespace name");
        }
        rc = getEndpoint().getKubernetesClient().replicationControllers().inNamespace(namespaceName).withName(rcName).get();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(rc);
    }

};