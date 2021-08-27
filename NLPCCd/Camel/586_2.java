//,temp,sample_2797.java,2,11,temp,sample_2911.java,2,13
//,3
public class xxx {
private void autoRegisterBeanDefinition(String id, BeanDefinition definition, ParserContext parserContext, String contextId) {
BeanDefinition existing = autoRegisterMap.get(id);
if (existing == null) {
autoRegisterMap.put(id, definition);
parserContext.registerComponent(new BeanComponentDefinition(definition, id));
if (LOG.isDebugEnabled()) {


log.info("registered default with id on camel context");
}
}
}

};