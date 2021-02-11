//,temp,AddClusterCmd.java,209,239,temp,RemoveVmwareDcCmd.java,60,84
//,3
public class xxx {
    @Override
    public void execute() {
        SuccessResponse response = new SuccessResponse();
        try {
            boolean result = _vmwareDatacenterService.removeVmwareDatacenter(this);
            if (result) {
                response.setResponseName(getCommandName());
                setResponseObject(response);
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to remove VMware datacenter from zone");
            }
        } catch (ResourceInUseException ex) {
            s_logger.warn("The zone has one or more resources (like cluster), hence not able to remove VMware datacenter from zone."
                + " Please remove all resource from zone, and retry. Exception: ", ex);
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