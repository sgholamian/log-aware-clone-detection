//,temp,XMLConverterHelper.java,155,185,temp,XmlConverter.java,1006,1036
//,3
public class xxx {
    public TransformerFactory createTransformerFactory() {
        TransformerFactory factory;
        TransformerFactoryConfigurationError cause;
        try {
            factory = TransformerFactory.newInstance();
        } catch (TransformerFactoryConfigurationError e) {
            cause = e;
            // try fallback from the JDK
            try {
                LOG.debug(
                        "Cannot create/load TransformerFactory due: {}. Will attempt to use JDK fallback TransformerFactory: {}",
                        e.getMessage(), JDK_FALLBACK_TRANSFORMER_FACTORY);
                factory = TransformerFactory.newInstance(JDK_FALLBACK_TRANSFORMER_FACTORY, null);
            } catch (Exception t) {
                // okay we cannot load fallback then throw original exception
                throw cause;
            }
        }
        LOG.debug("Created TransformerFactory: {}", factory);

        // Enable the Security feature by default
        try {
            factory.setFeature(javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING, true);
        } catch (TransformerConfigurationException e) {
            LOG.warn("TransformerFactory doesn't support the feature {} with value {}, due to {}.",
                    javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING, "true", e.getMessage(), e);
        }
        factory.setErrorListener(new XmlErrorListener());
        configureSaxonTransformerFactory(factory);
        return factory;
    }

};