//,temp,AlertGenerator.java,70,114,temp,ActionEventUtils.java,190,255
//,3
public class xxx {
    private static void publishOnEventBus(long userId, long accountId, String eventCategory, String eventType, Event.State state, String description) {
        String configKey = Config.PublishActionEvent.key();
        String value = s_configDao.getValue(configKey);
        boolean configValue = Boolean.parseBoolean(value);
        if(!configValue)
            return;
        try {
            s_eventBus = ComponentContext.getComponent(EventBus.class);
        } catch (NoSuchBeanDefinitionException nbe) {
            return; // no provider is configured to provide events bus, so just return
        }

        // get the entity details for which ActionEvent is generated
        String entityType = null;
        String entityUuid = null;
        CallContext context = CallContext.current();
        //Get entity Class(Example - VirtualMachine.class) from the event Type eg. - VM.CREATE
        Class<?> entityClass = EventTypes.getEntityClassForEvent(eventType);
        if (entityClass != null){
            //Get uuid from id
            Object param = context.getContextParameter(entityClass);
            if(param != null){
                try {
                    entityUuid = getEntityUuid(entityClass, param);
                    entityType = entityClass.getName();
                } catch (Exception e){
                    s_logger.debug("Caught exception while finding entityUUID, moving on");
                }
            }
        }

        org.apache.cloudstack.framework.events.Event event =
            new org.apache.cloudstack.framework.events.Event(ManagementService.Name, eventCategory, eventType, EventTypes.getEntityForEvent(eventType), entityUuid);

        Map<String, String> eventDescription = new HashMap<String, String>();
        Project project = s_projectDao.findByProjectAccountId(accountId);
        Account account = s_accountDao.findById(accountId);
        User user = s_userDao.findById(userId);
        // if account has been deleted, this might be called during cleanup of resources and results in null pointer
        if (account == null)
            return;
        if (user == null)
            return;
        if (project != null)
            eventDescription.put("project", project.getUuid());
        eventDescription.put("user", user.getUuid());
        eventDescription.put("account", account.getUuid());
        eventDescription.put("event", eventType);
        eventDescription.put("status", state.toString());
        eventDescription.put("entity", entityType);
        eventDescription.put("entityuuid", entityUuid);
        //Put all the first class entities that are touched during the action. For now atleast put in the vmid.
        populateFirstClassEntities(eventDescription);
        eventDescription.put("description", description);

        String eventDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(new Date());
        eventDescription.put("eventDateTime", eventDate);

        event.setDescription(eventDescription);

        try {
            s_eventBus.publish(event);
        } catch (EventBusException e) {
            s_logger.warn("Failed to publish action event on the the event bus.");
        }
    }

};