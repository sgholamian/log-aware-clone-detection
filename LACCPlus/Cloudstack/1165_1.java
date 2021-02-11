//,temp,ServiceInstanceModel.java,238,261,temp,ServiceInstanceModel.java,145,173
//,3
public class xxx {
    private ServiceTemplate locateServiceTemplate(ModelController controller) {
        ServiceTemplate tmpl;
        try {
            ApiConnector api = controller.getApiAccessor();
            tmpl = (ServiceTemplate)api.findById(ServiceTemplate.class, _templateId);
        } catch (IOException ex) {
            s_logger.warn("service-template read", ex);
            throw new CloudRuntimeException("Unable to create service-template object", ex);
        }
        if (tmpl == null) {
            tmpl = new ServiceTemplate();
            tmpl.setName(_templateName);
            tmpl.setUuid(_templateId);
            ServiceTemplateType props = new ServiceTemplateType("in-network", null, _templateUrl, false, null);
            tmpl.setProperties(props);
            try {
                ApiConnector api = controller.getApiAccessor();
                api.create(tmpl);
            } catch (IOException ex) {
                throw new CloudRuntimeException("Unable to create service-template object", ex);
            }
        }
        return tmpl;
    }

};