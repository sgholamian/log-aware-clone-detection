//,temp,sample_4716.java,2,16,temp,sample_4715.java,2,17
//,3
public class xxx {
public void dummy_method(){
Class<?> processorClass = processorDefinition.getClass();
String shortName = processorDefinition.getShortName();
if (processorDefinition instanceof BeanDefinition) {
BeanProcessor beanProcessor = (BeanProcessor) nextTarget;
processorClass = beanProcessor.getBean().getClass();
} else if (processorDefinition instanceof ProcessDefinition) {
DelegateSyncProcessor syncProcessor = (DelegateSyncProcessor) nextTarget;
processorClass = syncProcessor.getProcessor().getClass();
}
if (!processorClass.isAnnotationPresent(XRayTrace.class)) {


log.info("does not contain an trace annotation skipping interception");
}
}

};