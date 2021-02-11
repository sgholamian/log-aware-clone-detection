//,temp,ApiResponseHelper.java,2429,2446,temp,ServiceManagerImpl.java,233,250
//,3
public class xxx {
    @Override
    public ServiceInstanceResponse createServiceInstanceResponse(long instanceId) {
        s_logger.debug("ServiceInstance response for id: " + instanceId);

        UserVmVO vm = _vmDao.findById(instanceId);
        ServiceInstanceResponse response = new ServiceInstanceResponse();
        response.setId(vm.getUuid());
        Account owner = _accountService.getAccount(vm.getAccountId());

        if (owner.getType() == Account.ACCOUNT_TYPE_PROJECT) {
            Project project = ApiDBUtils.findProjectByProjectAccountIdIncludingRemoved(owner.getAccountId());
            response.setProjectId(project.getUuid());
            response.setProjectName(project.getName());
        } else {
            response.setAccountName(owner.getAccountName());
        }
        return response;
    }

};