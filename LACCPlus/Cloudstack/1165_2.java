//,temp,ServiceInstanceModel.java,238,261,temp,ServiceInstanceModel.java,145,173
//,3
public class xxx {
    private ServiceInstance createServiceInstance(ModelController controller) {
        Project project = null;
        if (_projectId != null) {
            try {
                ApiConnector api = controller.getApiAccessor();
                project = (Project)api.findById(Project.class, _projectId);
            } catch (IOException ex) {
                s_logger.warn("project read", ex);
                throw new CloudRuntimeException("Unable to create service-instance object", ex);
            }
        }

        ServiceInstance si_obj = new ServiceInstance();
        if (project != null) {
            si_obj.setParent(project);
        }
        si_obj.setName(getName());
        si_obj.setServiceTemplate(_tmpl);
        si_obj.setProperties(new ServiceInstanceType(false, _mgmtName, _leftName, null, _rightName, null, new ServiceInstanceType.ServiceScaleOutType(1, false)));
        try {
            ApiConnector api = controller.getApiAccessor();
            api.create(si_obj);
        } catch (IOException ex) {
            s_logger.warn("service-instance create", ex);
            throw new CloudRuntimeException("Unable to create service-instance object", ex);
        }

        return si_obj;
    }

};