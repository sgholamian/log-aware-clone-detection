//,temp,RegisterTemplateCmdByAdmin.java,40,60,temp,RegisterTemplateCmd.java,299,319
//,3
public class xxx {
    @Override
    public void execute() throws ResourceAllocationException {
        try {
            validateParameters();

            VirtualMachineTemplate template = _templateService.registerTemplate(this);
            if (template != null) {
                ListResponse<TemplateResponse> response = new ListResponse<TemplateResponse>();
                List<TemplateResponse> templateResponses = _responseGenerator.createTemplateResponses(ResponseView.Restricted,
                        template, getZoneIds(), false);
                response.setResponses(templateResponses);
                response.setResponseName(getCommandName());
                setResponseObject(response);
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to register template");
            }
        } catch (URISyntaxException ex1) {
            s_logger.info(ex1);
            throw new ServerApiException(ApiErrorCode.PARAM_ERROR, ex1.getMessage());
        }
    }

};