//,temp,OpenshiftBuildsProducer.java,109,126,temp,OpenshiftBuildConfigsProducer.java,110,127
//,2
public class xxx {
    protected void doGetBuildConfig(Exchange exchange, String operation) throws Exception {
        BuildConfig buildConfig = null;
        String buildConfigName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_BUILD_CONFIG_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(buildConfigName)) {
            LOG.error("Get a specific Build Config require specify a Build Config name");
            throw new IllegalArgumentException("Get a specific Build Config require specify a Build Config name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Get a specific Build Config require specify a namespace name");
            throw new IllegalArgumentException("Get a specific Build Config require specify a namespace name");
        }
        buildConfig = getEndpoint().getKubernetesClient().adapt(OpenShiftClient.class).buildConfigs().inNamespace(namespaceName)
                .withName(buildConfigName).get();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(buildConfig);
    }

};