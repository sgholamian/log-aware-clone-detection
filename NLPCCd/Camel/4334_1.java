//,temp,sample_4716.java,2,16,temp,sample_4715.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (processorDefinition instanceof BeanDefinition) {
BeanProcessor beanProcessor = (BeanProcessor) nextTarget;
processorClass = beanProcessor.getBean().getClass();
} else if (processorDefinition instanceof ProcessDefinition) {
DelegateSyncProcessor syncProcessor = (DelegateSyncProcessor) nextTarget;
processorClass = syncProcessor.getProcessor().getClass();
}
if (!processorClass.isAnnotationPresent(XRayTrace.class)) {
return new DelegateAsyncProcessor(target);
}


log.info("wrapping process definition of target in order for recording its trace");
}

};