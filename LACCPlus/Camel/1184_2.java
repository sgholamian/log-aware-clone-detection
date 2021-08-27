//,temp,KubernetesNodesProducer.java,141,151,temp,KubernetesJobProducer.java,155,170
//,3
public class xxx {
    protected void doDeleteJob(Exchange exchange, String operation) throws Exception {
        String jobName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_JOB_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(jobName)) {
            LOG.error("Delete a specific job require specify a job name");
            throw new IllegalArgumentException("Delete a specific job require specify a job name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Delete a specific job require specify a namespace name");
            throw new IllegalArgumentException("Delete a specific job require specify a namespace name");
        }

        getEndpoint().getKubernetesClient().batch().jobs().inNamespace(namespaceName).withName(jobName).delete();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
    }

};