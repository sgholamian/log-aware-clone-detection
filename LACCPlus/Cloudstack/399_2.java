//,temp,ScaleSystemVMCmd.java,102,134,temp,MigrateSystemVMCmd.java,110,142
//,3
public class xxx {
    @Override
    public void execute() {

        Host destinationHost = _resourceService.getHost(getHostId());
        if (destinationHost == null) {
            throw new InvalidParameterValueException("Unable to find the host to migrate the VM, host id=" + getHostId());
        }
        try {
            CallContext.current().setEventDetails("VM Id: " + this._uuidMgr.getUuid(VirtualMachine.class, getVirtualMachineId()) + " to host Id: " + this._uuidMgr.getUuid(Host.class, getHostId()));
            //FIXME : Should not be calling UserVmService to migrate all types of VMs - need a generic VM layer
            VirtualMachine migratedVm = _userVmService.migrateVirtualMachine(getVirtualMachineId(), destinationHost);
            if (migratedVm != null) {
                // return the generic system VM instance response
                SystemVmResponse response = _responseGenerator.createSystemVmResponse(migratedVm);
                response.setResponseName(getCommandName());
                setResponseObject(response);
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to migrate the system vm");
            }
        } catch (ResourceUnavailableException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.RESOURCE_UNAVAILABLE_ERROR, ex.getMessage());
        } catch (ConcurrentOperationException e) {
            s_logger.warn("Exception: ", e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        } catch (ManagementServerException e) {
            s_logger.warn("Exception: ", e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        } catch (VirtualMachineMigrationException e) {
            s_logger.warn("Exception: ", e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

};