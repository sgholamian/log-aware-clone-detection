//,temp,XMLConverterHelper.java,119,153,temp,XmlConverter.java,950,984
//,3
public class xxx {
    public DocumentBuilderFactory createDocumentBuilderFactory() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);
        try {
            // Set secure processing
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);
        } catch (ParserConfigurationException e) {
            LOG.warn("DocumentBuilderFactory doesn't support the feature {} with value {}, due to {}.",
                    XMLConstants.FEATURE_SECURE_PROCESSING, true, e.getMessage(), e);
        }
        try {
            // Disable the external-general-entities by default
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        } catch (ParserConfigurationException e) {
            LOG.warn("DocumentBuilderFactory doesn't support the feature {} with value {}, due to {}.",
                    "http://xml.org/sax/features/external-general-entities", false, e.getMessage(), e);
        }
        // setup the SecurityManager by default if it's apache xerces
        try {
            Class<?> smClass = ObjectHelper.loadClass("org.apache.xerces.util.SecurityManager");
            if (smClass != null) {
                Object sm = smClass.getDeclaredConstructor().newInstance();
                // Here we just use the default setting of the SeurityManager
                factory.setAttribute("http://apache.org/xml/properties/security-manager", sm);
            }
        } catch (Exception e) {
            LOG.warn("DocumentBuilderFactory doesn't support the attribute {}, due to {}.",
                    "http://apache.org/xml/properties/security-manager", e.getMessage(), e);
        }
        // setup the feature from the system property
        setupFeatures(factory);
        return factory;
    }

};