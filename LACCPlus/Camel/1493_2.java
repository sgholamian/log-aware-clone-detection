//,temp,KubernetesNamespacesProducer.java,110,120,temp,KubernetesJobProducer.java,111,127
//,3
public class xxx {
    protected void doGetJob(Exchange exchange, String operation) throws Exception {
        Job job = null;
        String jobName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_JOB_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(jobName)) {
            LOG.error("Get a specific job require specify a job name");
            throw new IllegalArgumentException("Get a specific job require specify a job name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Get a specific job require specify a namespace name");
            throw new IllegalArgumentException("Get a specific job require specify a namespace name");
        }
        job = getEndpoint().getKubernetesClient().batch().jobs().inNamespace(namespaceName).withName(jobName).get();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(job);
    }

};