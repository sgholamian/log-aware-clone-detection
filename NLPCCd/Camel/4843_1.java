//,temp,sample_510.java,2,18,temp,sample_509.java,2,18
//,2
public class xxx {
public void dummy_method(){
String xpathFactoryClassName = factoryClassName;
if (objectModelUri.equals(SAXON_OBJECT_MODEL_URI) && (xpathFactoryClassName == null || SAXON_FACTORY_CLASS_NAME.equals(xpathFactoryClassName))) {
try {
if (camelContext != null) {
Class<XPathFactory> clazz = camelContext.getClassResolver().resolveClass(SAXON_FACTORY_CLASS_NAME, XPathFactory.class);
if (clazz != null) {
xpathFactory = camelContext.getInjector().newInstance(clazz);
}
}
} catch (Throwable e) {


log.info("error creating saxon xpathfactory this exception is ignored");
}
}
}

};