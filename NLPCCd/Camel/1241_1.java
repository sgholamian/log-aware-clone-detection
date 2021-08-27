//,temp,sample_3793.java,2,13,temp,sample_3794.java,2,17
//,3
public class xxx {
private static Set<Method> findSetterMethods(Class<?> clazz, String name, Object value, boolean allowBuilderPattern) {
Set<Method> candidates = findSetterMethods(clazz, name, allowBuilderPattern);
if (candidates.isEmpty()) {
return candidates;
} else if (candidates.size() == 1) {
return candidates;
} else {


log.info("found suitable setter methods for setting");
}
}

};