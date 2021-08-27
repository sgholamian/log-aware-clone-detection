//,temp,XMLConverterHelper.java,246,275,temp,XmlConverter.java,906,935
//,3
public class xxx {
    protected void setupFeatures(DocumentBuilderFactory factory) {
        Properties properties = System.getProperties();
        List<String> features = new ArrayList<>();
        for (Map.Entry<Object, Object> prop : properties.entrySet()) {
            String key = (String) prop.getKey();
            if (key.startsWith(XmlConverter.DOCUMENT_BUILDER_FACTORY_FEATURE)) {
                String uri = StringHelper.after(key, ":");
                Boolean value = Boolean.valueOf((String) prop.getValue());
                try {
                    factory.setFeature(uri, value);
                    features.add("feature " + uri + " value " + value);
                } catch (ParserConfigurationException e) {
                    LOG.warn("DocumentBuilderFactory doesn't support the feature {} with value {}, due to {}.", uri,
                            value, e.getMessage(), e);
                }
            }
        }
        if (!features.isEmpty()) {
            StringBuilder featureString = new StringBuilder();
            // just log the configured feature
            for (String feature : features) {
                if (featureString.length() != 0) {
                    featureString.append(", ");
                }
                featureString.append(feature);
            }
            LOG.info("DocumentBuilderFactory has been set with features {{}}.", featureString);
        }

    }

};