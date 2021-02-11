//,temp,NetworkStateListener.java,68,99,temp,UsageEventUtils.java,168,211
//,3
public class xxx {
  private void pubishOnEventBus(String event, String status, Network vo, State oldState, State newState) {

        String configKey = "publish.resource.state.events";
        String value = _configDao.getValue(configKey);
        boolean configValue = Boolean.parseBoolean(value);
        if(!configValue)
            return;
        try {
            s_eventBus = ComponentContext.getComponent(EventBus.class);
        } catch (NoSuchBeanDefinitionException nbe) {
            return; // no provider is configured to provide events bus, so just return
        }

        String resourceName = getEntityFromClassName(Network.class.getName());
        org.apache.cloudstack.framework.events.Event eventMsg =
            new org.apache.cloudstack.framework.events.Event("management-server", EventCategory.RESOURCE_STATE_CHANGE_EVENT.getName(), event, resourceName, vo.getUuid());
        Map<String, String> eventDescription = new HashMap<String, String>();
        eventDescription.put("resource", resourceName);
        eventDescription.put("id", vo.getUuid());
        eventDescription.put("old-state", oldState.name());
        eventDescription.put("new-state", newState.name());

        String eventDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(new Date());
        eventDescription.put("eventDateTime", eventDate);

        eventMsg.setDescription(eventDescription);
        try {
            s_eventBus.publish(eventMsg);
        } catch (EventBusException e) {
            s_logger.warn("Failed to publish state change event on the the event bus.");
        }
    }

};