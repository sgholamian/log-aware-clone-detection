//,temp,KubernetesJobProducer.java,129,153,temp,KubernetesConfigMapsProducer.java,125,150
//,3
public class xxx {
    protected void doCreateJob(Exchange exchange, String operation) throws Exception {
        Job job = null;
        String jobName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_JOB_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        JobSpec jobSpec = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_JOB_SPEC, JobSpec.class);
        if (ObjectHelper.isEmpty(jobName)) {
            LOG.error("Create a specific job require specify a job name");
            throw new IllegalArgumentException("Create a specific job require specify a job name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Create a specific job require specify a namespace name");
            throw new IllegalArgumentException("Create a specific job require specify a namespace name");
        }
        if (ObjectHelper.isEmpty(jobSpec)) {
            LOG.error("Create a specific job require specify a hpa spec bean");
            throw new IllegalArgumentException("Create a specific job require specify a hpa spec bean");
        }
        Map<String, String> labels = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_JOB_LABELS, Map.class);
        Job jobCreating = new JobBuilder().withNewMetadata().withName(jobName).withLabels(labels).endMetadata()
                .withSpec(jobSpec).build();
        job = getEndpoint().getKubernetesClient().batch().jobs().inNamespace(namespaceName).create(jobCreating);

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(job);
    }

};