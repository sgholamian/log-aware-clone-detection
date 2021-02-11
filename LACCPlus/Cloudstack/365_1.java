//,temp,NetworkPolicyModel.java,234,282,temp,VirtualMachineModel.java,309,358
//,3
public class xxx {
    @Override
    public void update(ModelController controller) throws InternalErrorException, IOException {
        ApiConnector api = controller.getApiAccessor();
        if (_project == null) {
            s_logger.debug("Project is null for the policy: " + _name);
            throw new IOException("Project is null for the policy: " + _name);
        }

        NetworkPolicy policy = _policy;

        if (policy == null) {
            try {
                String policyId = api.findByName(NetworkPolicy.class, _project, _name);
                if (policyId != null) {
                    policy = _policy = (NetworkPolicy) api.findById(NetworkPolicy.class, policyId);
                }
                if (policy == null) {
                    policy = new NetworkPolicy();
                    policy.setUuid(_uuid);
                    policy.setName(_name);
                    policy.setParent(_project);
                }
            } catch (IOException ex) {
                s_logger.warn("network-policy read", ex);
                return;
            }
        }

        policy.setEntries(_policyMap);
        if (_policy == null) {
            try {
                api.create(policy);
            } catch (Exception ex) {
                s_logger.debug("network policy create", ex);
                throw new CloudRuntimeException("Failed to create network policy", ex);
            }
            _policy = policy;
        } else {
            try {
                api.update(policy);
            } catch (IOException ex) {
                s_logger.warn("network policy update", ex);
                throw new CloudRuntimeException("Unable to update network policy", ex);
            }
        }
        for (ModelObject successor: successors()) {
            successor.update(controller);
        }
    }

};