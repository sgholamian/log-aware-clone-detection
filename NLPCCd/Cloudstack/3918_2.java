//,temp,sample_550.java,2,17,temp,sample_549.java,2,17
//,2
public class xxx {
public void dummy_method(){
_enhancer = new Enhancer();
_enhancer.setSuperclass(_entityBeanType);
_enhancer.setCallbackFilter(s_callbackFilter);
_enhancer.setCallbacks(callbacks);
_factory = (Factory)_enhancer.create();
_searchEnhancer = new Enhancer();
_searchEnhancer.setSuperclass(_entityBeanType);
_searchEnhancer.setCallback(new UpdateBuilder(this));
if (s_logger.isTraceEnabled()) {
s_logger.trace("Remove SQL: " + (_removeSql != null ? _removeSql.first() : "No remove sql"));


log.info("select by id sql");
}
}

};