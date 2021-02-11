//,temp,ScaleVMCmd.java,136,162,temp,AddVmwareDcCmd.java,99,128
//,3
public class xxx {
    @Override
    public void execute() {
        try {
            VmwareDatacenterResponse response = new VmwareDatacenterResponse();
            VmwareDatacenterVO result = _vmwareDatacenterService.addVmwareDatacenter(this);
            if (result != null) {
                response.setId(result.getUuid());
                response.setName(result.getVmwareDatacenterName());
                response.setResponseName(getCommandName());
                response.setObjectName("vmwaredc");
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to add VMware Datacenter to zone.");
            }
            this.setResponseObject(response);
        } catch (DiscoveryException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
        } catch (ResourceInUseException ex) {
            s_logger.warn("Exception: ", ex);
            ServerApiException e = new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
            for (String proxyObj : ex.getIdProxyList()) {
                e.addProxyObject(proxyObj);
            }
            throw e;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        } catch (CloudRuntimeException runtimeEx) {
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, runtimeEx.getMessage());
        }
    }

};