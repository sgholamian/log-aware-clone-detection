//,temp,sample_2603.java,2,17,temp,sample_2604.java,2,19
//,3
public class xxx {
private PropertyEditor lookupEditor(Class<?> type) {
if (misses.containsKey(type)) {
return null;
}
synchronized (cache) {
PropertyEditor editor = cache.get(type);
if (editor == null) {
editor = PropertyEditorManager.findEditor(type);
if (editor != null) {


log.info("found property editor for type");
}
}
}
}

};