//,temp,HypervisorTemplateAdapter.java,399,444,temp,TemplateServiceImpl.java,632,671
//,3
public class xxx {
    protected Void createTemplateAsyncCallBack(AsyncCallbackDispatcher<TemplateServiceImpl, TemplateApiResult> callback,
                                               TemplateOpContext<TemplateApiResult> context) {
        TemplateInfo template = context.template;
        TemplateApiResult result = callback.getResult();
        if (result.isSuccess()) {
            VMTemplateVO tmplt = _templateDao.findById(template.getId());
            // need to grant permission for public templates
            if (tmplt.isPublicTemplate()) {
                _messageBus.publish(null, TemplateManager.MESSAGE_REGISTER_PUBLIC_TEMPLATE_EVENT, PublishScope.LOCAL, tmplt.getId());
            }
            long accountId = tmplt.getAccountId();
            if (template.getSize() != null) {
                // publish usage event
                String etype = EventTypes.EVENT_TEMPLATE_CREATE;
                if (tmplt.getFormat() == ImageFormat.ISO) {
                    etype = EventTypes.EVENT_ISO_CREATE;
                }
                // get physical size from template_store_ref table
                long physicalSize = 0;
                DataStore ds = template.getDataStore();
                TemplateDataStoreVO tmpltStore = _vmTemplateStoreDao.findByStoreTemplate(ds.getId(), template.getId());
                if (tmpltStore != null) {
                    physicalSize = tmpltStore.getPhysicalSize();
                } else {
                    s_logger.warn("No entry found in template_store_ref for template id: " + template.getId() + " and image store id: " + ds.getId() +
                            " at the end of registering template!");
                }
                Scope dsScope = ds.getScope();
                if (dsScope.getScopeId() != null) {
                    UsageEventUtils.publishUsageEvent(etype, template.getAccountId(), dsScope.getScopeId(), template.getId(), template.getName(), null, null,
                            physicalSize, template.getSize(), VirtualMachineTemplate.class.getName(), template.getUuid());
                } else {
                    s_logger.warn("Zone scope image store " + ds.getId() + " has a null scope id");
                }
                _resourceLimitMgr.incrementResourceCount(accountId, Resource.ResourceType.secondary_storage, template.getSize());
            }
        }

        return null;
    }

};