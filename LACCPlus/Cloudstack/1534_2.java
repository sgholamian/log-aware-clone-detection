//,temp,CopyTemplateCmdByAdmin.java,40,71,temp,CopyTemplateCmd.java,166,197
//,2
public class xxx {
    @Override
    public void execute() throws ResourceAllocationException {
        try {
            if (destZoneId == null && (destZoneIds == null || destZoneIds.size() == 0))
                throw new ServerApiException(ApiErrorCode.PARAM_ERROR,
                        "Either destzoneid or destzoneids parameters have to be specified.");

            if (destZoneId != null && destZoneIds != null && destZoneIds.size() != 0)
                throw new ServerApiException(ApiErrorCode.PARAM_ERROR,
                        "Both destzoneid and destzoneids cannot be specified at the same time.");

            CallContext.current().setEventDetails(getEventDescription());
            VirtualMachineTemplate template = _templateService.copyTemplate(this);

            if (template != null){
                List<TemplateResponse> listResponse = _responseGenerator.createTemplateResponses(ResponseView.Restricted,
                                                            template, getDestinationZoneIds(), false);
                TemplateResponse response = new TemplateResponse();
                if (listResponse != null && !listResponse.isEmpty()) {
                    response = listResponse.get(0);
                }

                response.setResponseName(getCommandName());
                setResponseObject(response);
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to copy template");
            }
        } catch (StorageUnavailableException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.RESOURCE_UNAVAILABLE_ERROR, ex.getMessage());
        }
    }

};