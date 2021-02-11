//,temp,NetworkStateListener.java,68,99,temp,UsageEventUtils.java,168,211
//,3
public class xxx {
    private static void publishUsageEvent(String usageEventType, Long accountId, Long zoneId, String resourceType, String resourceUUID) {
        String configKey = "publish.usage.events";
        String value = s_configDao.getValue(configKey);
        boolean configValue = Boolean.parseBoolean(value);
        if( !configValue)
            return;
        try {
            s_eventBus = ComponentContext.getComponent(EventBus.class);
        } catch (NoSuchBeanDefinitionException nbe) {
            return; // no provider is configured to provide events bus, so just return
        }

        Account account = s_accountDao.findById(accountId);
        DataCenterVO dc = s_dcDao.findById(zoneId);

        // if account has been deleted, this might be called during cleanup of resources and results in null pointer
        if (account == null)
            return;

        // if an invalid zone is passed in, create event without zone UUID
        String zoneUuid = null;
        if (dc != null)
            zoneUuid = dc.getUuid();

        Event event = new Event(Name, EventCategory.USAGE_EVENT.getName(), usageEventType, resourceType, resourceUUID);

        Map<String, String> eventDescription = new HashMap<String, String>();
        eventDescription.put("account", account.getUuid());
        eventDescription.put("zone", zoneUuid);
        eventDescription.put("event", usageEventType);
        eventDescription.put("resource", resourceType);
        eventDescription.put("id", resourceUUID);

        String eventDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(new Date());
        eventDescription.put("eventDateTime", eventDate);

        event.setDescription(eventDescription);

        try {
            s_eventBus.publish(event);
        } catch (EventBusException e) {
            s_logger.warn("Failed to publish usage event on the the event bus.");
        }
    }

};