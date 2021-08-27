//,temp,sample_5925.java,2,16,temp,sample_5926.java,2,17
//,3
public class xxx {
public void dummy_method(){
ModelChannel channel = createChannel(routeContext);
channel.setNextProcessor(processor);
addInterceptStrategies(routeContext, channel, routeContext.getCamelContext().getInterceptStrategies());
addInterceptStrategies(routeContext, channel, routeContext.getInterceptStrategies());
addInterceptStrategies(routeContext, channel, this.getInterceptStrategies());
ProcessorDefinition<?> defn = (ProcessorDefinition<?>) this;
channel.setChildDefinition(child);
channel.initChannel(defn, routeContext);
if (defn instanceof TryDefinition || defn instanceof CatchDefinition || defn instanceof FinallyDefinition) {
} else if (ProcessorDefinitionHelper.isParentOfType(TryDefinition.class, defn, true) || ProcessorDefinitionHelper.isParentOfType(CatchDefinition.class, defn, true) || ProcessorDefinitionHelper.isParentOfType(FinallyDefinition.class, defn, true)) {


log.info("is part of dotry docatch dofinally so no error handler is applied");
}
}

};