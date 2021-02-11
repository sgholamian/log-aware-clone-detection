//,temp,DBSyncGeneric.java,150,158,temp,DBSyncGeneric.java,140,148
//,2
public class xxx {
    private Boolean filter(Class<?> cls, Object... parameters) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String filterMethod = filterMethodPrefix + getClassName(cls);
        Method method = _methodMap.get(filterMethod);
        if (method == null) {
            s_logger.debug("Method not implemented: " + getClassName(_scope.getClass()) + ":" + filterMethod);
            return false;
        }
        return (Boolean)method.invoke(_scope, parameters);
    }

};