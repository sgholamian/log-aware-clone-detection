//,temp,DBSyncGeneric.java,150,158,temp,DBSyncGeneric.java,140,148
//,2
public class xxx {
    private Boolean equal(Class<?> cls, Object... parameters) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String equalMethod = equalMethodPrefix + getClassName(cls);
        Method method = _methodMap.get(equalMethod);
        if (method == null) {
            s_logger.debug("Method not implemented: " + getClassName(_scope.getClass()) + ":" + equalMethod);
            return true;
        }
        return (Boolean)method.invoke(_scope, parameters);
    }

};