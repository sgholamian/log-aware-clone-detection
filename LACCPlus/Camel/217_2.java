//,temp,KubernetesNodesProducer.java,107,118,temp,KubernetesPersistentVolumesProducer.java,99,110
//,2
public class xxx {
    protected void doGetPersistentVolume(Exchange exchange, String operation) throws Exception {
        PersistentVolume pv = null;
        String pvName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_PERSISTENT_VOLUME_NAME, String.class);
        if (ObjectHelper.isEmpty(pvName)) {
            LOG.error("Get a specific Persistent Volume require specify a Persistent Volume name");
            throw new IllegalArgumentException("Get a specific Persistent Volume require specify a Persistent Volume name");
        }
        pv = getEndpoint().getKubernetesClient().persistentVolumes().withName(pvName).get();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(pv);
    }

};