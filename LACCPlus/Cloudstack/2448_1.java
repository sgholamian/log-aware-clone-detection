//,temp,VmWorkJobWakeupDispatcher.java,122,146,temp,VmwareManagerImpl.java,704,730
//,3
public class xxx {
    private Method getHandler(String wakeupHandler) {

        synchronized (_handlerMap) {
            Class<?> clz = _vmMgr.getClass();
            Method method = _handlerMap.get(wakeupHandler);
            if (method != null)
                return method;

            try {
                method = clz.getMethod(wakeupHandler);
                method.setAccessible(true);
            } catch (SecurityException e) {
                assert (false);
                s_logger.error("Unexpected exception", e);
                return null;
            } catch (NoSuchMethodException e) {
                assert (false);
                s_logger.error("Unexpected exception", e);
                return null;
            }

            _handlerMap.put(wakeupHandler, method);
            return method;
        }
    }

};