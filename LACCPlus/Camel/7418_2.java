//,temp,KubernetesPodsProducer.java,98,113,temp,KubernetesJobProducer.java,94,109
//,3
public class xxx {
    protected void doListJobByLabel(Exchange exchange, String operation) {
        Map<String, String> labels = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_JOB_LABELS, Map.class);
        if (ObjectHelper.isEmpty(labels)) {
            LOG.error("Get Job by labels require specify a labels set");
            throw new IllegalArgumentException("Get Job by labels require specify a labels set");
        }

        MixedOperation<Job, JobList, ScalableResource<Job>> jobs = getEndpoint().getKubernetesClient().batch().jobs();
        for (Map.Entry<String, String> entry : labels.entrySet()) {
            jobs.withLabel(entry.getKey(), entry.getValue());
        }
        JobList jobList = jobs.list();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(jobList.getItems());
    }

};