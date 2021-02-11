//,temp,ServerEventHandlerImpl.java,150,163,temp,ServerEventHandlerImpl.java,116,131
//,2
public class xxx {
    void defaultCreateHandler(String subject, String topic, org.apache.cloudstack.framework.events.Event event) {

        s_logger.debug("Default handler is invoked for subject: " + subject + "; topic: " + topic);
        s_logger.debug("description: " + event.getDescription());
        s_logger.debug("category: " + event.getEventCategory());
        s_logger.debug("type: " + event.getResourceType());
        s_logger.debug("event-type: " + event.getEventType());

        Class<?> cls = _classMap.get(event.getResourceType());

        if (cls != null) {
            _dbSync.syncClass(cls);
        }

        return;
    }

};