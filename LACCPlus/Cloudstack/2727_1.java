//,temp,AlertGenerator.java,70,114,temp,SnapshotStateListener.java,78,111
//,3
public class xxx {
    public static void publishAlertOnEventBus(String alertType, long dataCenterId, Long podId, String subject, String body) {

        String configKey = Config.PublishAlertEvent.key();
        String value = s_configDao.getValue(configKey);
        boolean configValue = Boolean.parseBoolean(value);
        if(!configValue)
            return;
        try {
            s_eventBus = ComponentContext.getComponent(EventBus.class);
        } catch (NoSuchBeanDefinitionException nbe) {
            return; // no provider is configured to provide events bus, so just return
        }

        org.apache.cloudstack.framework.events.Event event =
            new org.apache.cloudstack.framework.events.Event(ManagementService.Name, EventCategory.ALERT_EVENT.getName(), alertType, null, null);

        Map<String, String> eventDescription = new HashMap<String, String>();
        DataCenterVO dc = s_dcDao.findById(dataCenterId);
        HostPodVO pod = s_podDao.findById(podId);

        eventDescription.put("event", alertType);
        if (dc != null) {
            eventDescription.put("dataCenterId", dc.getUuid());
        } else {
            eventDescription.put("dataCenterId", null);
        }
        if (pod != null) {
            eventDescription.put("podId", pod.getUuid());
        } else {
            eventDescription.put("podId", null);
        }
        eventDescription.put("subject", subject);
        eventDescription.put("body", body);

        String eventDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(new Date());
        eventDescription.put("eventDateTime", eventDate);

        event.setDescription(eventDescription);

        try {
            s_eventBus.publish(event);
        } catch (EventBusException e) {
            s_logger.warn("Failed to publish alert on the the event bus.");
        }
    }

};