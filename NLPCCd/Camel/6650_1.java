//,temp,sample_3384.java,2,7,temp,sample_5002.java,2,10
//,3
public class xxx {
private void createTransformerFactory() throws ClassNotFoundException {
Class<TransformerFactory> factoryClass = getCamelContext().getClassResolver().resolveMandatoryClass(SAXON_TRANSFORMER_FACTORY_CLASS_NAME, TransformerFactory.class, SchematronComponent.class.getClassLoader());


log.info("using transformerfactoryclass");
}

};