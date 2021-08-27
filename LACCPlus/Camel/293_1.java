//,temp,OpenshiftBuildsProducer.java,109,126,temp,OpenshiftBuildConfigsProducer.java,110,127
//,2
public class xxx {
    protected void doGetBuild(Exchange exchange, String operation) throws Exception {
        Build build = null;
        String buildName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_BUILD_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(buildName)) {
            LOG.error("Get a specific Build require specify a Build name");
            throw new IllegalArgumentException("Get a specific Build require specify a Build name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Get a specific Build require specify a namespace name");
            throw new IllegalArgumentException("Get a specific Build require specify a namespace name");
        }
        build = getEndpoint().getKubernetesClient().adapt(OpenShiftClient.class).builds().inNamespace(namespaceName)
                .withName(buildName).get();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(build);
    }

};