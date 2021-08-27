//,temp,XMLConverterHelper.java,191,222,temp,XmlConverter.java,1044,1075
//,2
public class xxx {
    public void configureSaxonTransformerFactory(TransformerFactory factory) {
        // check whether we have a Saxon TransformerFactory ("net.sf.saxon" for open source editions (HE / B)
        // and "com.saxonica" for commercial editions (PE / EE / SA))
        Class<?> factoryClass = factory.getClass();
        if (factoryClass.getName().startsWith("net.sf.saxon")
                || factoryClass.getName().startsWith("com.saxonica")) {

            // just in case there are multiple class loaders with different Saxon versions, use the
            // TransformerFactory's class loader to find Saxon support classes
            ClassLoader loader = factoryClass.getClassLoader();

            // try to find Saxon's MessageWarner class that redirects <xsl:message> to the ErrorListener
            Class<?> messageWarner = null;
            try {
                // Saxon >= 9.3
                messageWarner = loader.loadClass("net.sf.saxon.serialize.MessageWarner");
            } catch (ClassNotFoundException cnfe) {
                try {
                    // Saxon < 9.3 (including Saxon-B / -SA)
                    messageWarner = loader.loadClass("net.sf.saxon.event.MessageWarner");
                } catch (ClassNotFoundException cnfe2) {
                    LOG.warn("Error loading Saxon's net.sf.saxon.serialize.MessageWarner class from the classpath!"
                             + " <xsl:message> output will not be redirected to the ErrorListener!");
                }
            }

            if (messageWarner != null) {
                // set net.sf.saxon.FeatureKeys.MESSAGE_EMITTER_CLASS
                factory.setAttribute("http://saxon.sf.net/feature/messageEmitterClass", messageWarner.getName());
            }
        }
    }

};