//,temp,FloatingIpPoolModel.java,120,159,temp,VirtualMachineModel.java,309,358
//,3
public class xxx {
    @Override
    public void update(ModelController controller) throws InternalErrorException, IOException {
        assert _initialized;
        ApiConnector api = controller.getApiAccessor();

        VirtualMachine vm = _vm;
        if (vm == null) {
            _vm = vm = (VirtualMachine)api.findById(VirtualMachine.class, _uuid);
            if (vm == null) {
                vm = new VirtualMachine();
                if (_projectId != null) {
                    Project project;
                    try {
                        project = (Project)api.findById(Project.class, _projectId);
                    } catch (IOException ex) {
                        s_logger.debug("project read", ex);
                        throw new CloudRuntimeException("Failed to read project", ex);
                    }
                    vm.setParent(project);
                }
                vm.setName(_instanceName);
                vm.setUuid(_uuid);
            }
        }

        if (_serviceModel != null) {
            vm.setServiceInstance(_serviceModel.getServiceInstance());
        }

        if (_vm == null) {
            try {
                api.create(vm);
            } catch (Exception ex) {
                s_logger.debug("virtual-machine create", ex);
                throw new CloudRuntimeException("Failed to create virtual-machine", ex);
            }
            _vm = vm;
        } else {
            try {
                api.update(vm);
            } catch (IOException ex) {
                s_logger.warn("virtual-machine update", ex);
                throw new CloudRuntimeException("Unable to update virtual-machine object", ex);
            }
        }

        for (ModelObject successor : successors()) {
            successor.update(controller);
        }
    }

};