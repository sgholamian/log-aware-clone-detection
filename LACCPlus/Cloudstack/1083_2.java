//,temp,ListBaremetalPxeServersCmd.java,69,83,temp,UploadTemplateDirectDownloadCertificateCmd.java,59,74
//,3
public class xxx {
    @Override
    public void execute() {
        if (!hypervisor.equalsIgnoreCase("kvm")) {
            throw new ServerApiException(ApiErrorCode.PARAM_ERROR, "Currently supporting KVM hosts only");
        }

        try {
            LOG.debug("Uploading certificate " + name + " to agents for Direct Download");
            boolean result = directDownloadManager.uploadCertificateToHosts(certificate, name, hypervisor);
            SuccessResponse response = new SuccessResponse(getCommandName());
            response.setSuccess(result);
            setResponseObject(response);
        } catch (Exception e) {
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

};