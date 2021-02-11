//,temp,BaseCmd.java,366,392,temp,ActionEventUtils.java,289,306
//,3
public class xxx {
    public boolean isDisplay(){
        CallContext context = CallContext.current();
        Map<Object, Object> contextMap = context.getContextParameters();
        boolean isDisplay = true;

        // Iterate over all the first class entities in context and check their display property.
        for(Map.Entry<Object, Object> entry : contextMap.entrySet()){
            try{
                Object key = entry.getKey();
                Class clz = Class.forName((String)key);
                if(Displayable.class.isAssignableFrom(clz)){
                    final Object objVO = getEntityVO(clz, entry.getValue());
                    isDisplay = ((Displayable) objVO).isDisplay();
                }

                // If the flag is false break immediately
                if(!isDisplay)
                    break;
            } catch (Exception e){
                s_logger.trace("Caught exception while checking first class entities for display property, continuing on", e);
            }
        }

        context.setEventDisplayEnabled(isDisplay);
        return isDisplay;

    }

};