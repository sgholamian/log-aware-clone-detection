//,temp,sample_1861.java,2,12,temp,sample_1862.java,2,14
//,3
public class xxx {
protected void doStart() throws Exception {
ObjectHelper.notNull(getCamelContext(), "camelContext");
if (isResolvePropertyPlaceholders()) {
Component existing = CamelContextHelper.lookupPropertiesComponent(camelContext, false);
if (existing != null) {
CamelContextHelper.resolvePropertyPlaceholders(camelContext, this);
} else {


log.info("cannot resolve property placeholders on component as propertiescomponent is not in use");
}
}
}

};