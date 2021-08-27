//,temp,sample_505.java,2,14,temp,sample_506.java,2,15
//,3
public class xxx {
protected synchronized XPathExpression createXPathExpression() throws XPathExpressionException, XPathFactoryConfigurationException {
try {
start();
} catch (Exception e) {
throw new RuntimeExpressionException("Error starting XPathBuilder", e);
}
XPath xPath = getXPathFactory().newXPath();
if (!logNamespaces && LOG.isTraceEnabled()) {


log.info("creating new xpath expression in pool namespaces on xpath expression");
}
}

};