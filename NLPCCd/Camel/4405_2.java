//,temp,sample_5928.java,2,17,temp,sample_5927.java,2,17
//,3
public class xxx {
public void dummy_method(){
channel.setNextProcessor(processor);
addInterceptStrategies(routeContext, channel, routeContext.getCamelContext().getInterceptStrategies());
addInterceptStrategies(routeContext, channel, routeContext.getInterceptStrategies());
addInterceptStrategies(routeContext, channel, this.getInterceptStrategies());
ProcessorDefinition<?> defn = (ProcessorDefinition<?>) this;
channel.setChildDefinition(child);
channel.initChannel(defn, routeContext);
if (defn instanceof TryDefinition || defn instanceof CatchDefinition || defn instanceof FinallyDefinition) {
} else if (ProcessorDefinitionHelper.isParentOfType(TryDefinition.class, defn, true) || ProcessorDefinitionHelper.isParentOfType(CatchDefinition.class, defn, true) || ProcessorDefinitionHelper.isParentOfType(FinallyDefinition.class, defn, true)) {
} else if (defn instanceof OnExceptionDefinition || ProcessorDefinitionHelper.isParentOfType(OnExceptionDefinition.class, defn, true)) {


log.info("is part of onexception so no error handler is applied");
}
}

};