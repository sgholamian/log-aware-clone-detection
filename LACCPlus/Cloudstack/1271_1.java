//,temp,AddHostCmd.java,139,162,temp,AddClusterCmd.java,209,239
//,3
public class xxx {
    @Override
    public void execute() {
        try {
            List<? extends Host> result = _resourceService.discoverHosts(this);
            ListResponse<HostResponse> response = new ListResponse<HostResponse>();
            List<HostResponse> hostResponses = new ArrayList<HostResponse>();
            if (result != null && result.size() > 0) {
                for (Host host : result) {
                    HostResponse hostResponse = _responseGenerator.createHostResponse(host);
                    hostResponses.add(hostResponse);
                }
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to add host");
            }

            response.setResponses(hostResponses);
            response.setResponseName(getCommandName());

            this.setResponseObject(response);
        } catch (DiscoveryException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
        }
    }

};