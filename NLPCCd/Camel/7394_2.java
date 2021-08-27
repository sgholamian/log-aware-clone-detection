//,temp,sample_5402.java,2,21,temp,sample_5403.java,2,17
//,3
public class xxx {
public void dummy_method(){
Class<?> extensionClass = camelContext.getClassResolver().resolveMandatoryClass( SAXON_EXTENDED_FUNCTION_DEFINITION_CLASS_NAME, XsltComponent.class.getClassLoader() );
method = configuration.getClass().getMethod("registerExtensionFunction", extensionClass);
if (method != null) {
factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
for (Object extensionFunction : saxonExtensionFunctions) {
if (extensionClass.isInstance(extensionFunction)) {
method.invoke(configuration, extensionFunction);
}
}
} else {


log.info("unable to get reference to method registerextensionfunction on");
}
}

};