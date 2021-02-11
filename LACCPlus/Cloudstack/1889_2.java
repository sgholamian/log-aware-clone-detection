//,temp,BaseCmd.java,366,392,temp,ActionEventUtils.java,289,306
//,3
public class xxx {
    private static void populateFirstClassEntities(Map<String, String> eventDescription){

        CallContext context = CallContext.current();
        Map<Object, Object> contextMap = context.getContextParameters();

        for(Map.Entry<Object, Object> entry : contextMap.entrySet()){
            try{
                Class<?> clz = (Class<?>)entry.getKey();
                if(clz != null && Identity.class.isAssignableFrom(clz)){
                    String uuid = getEntityUuid(clz, entry.getValue());
                    eventDescription.put(ReflectUtil.getEntityName(clz), uuid);
                }
            } catch (Exception e){
                s_logger.trace("Caught exception while populating first class entities for event bus, moving on");
            }
        }

    }

};