//,temp,AddHostCmd.java,139,162,temp,AddClusterCmd.java,209,239
//,3
public class xxx {
    @Override
    public void execute() {
        try {
            List<? extends Cluster> result = _resourceService.discoverCluster(this);
            ListResponse<ClusterResponse> response = new ListResponse<ClusterResponse>();
            List<ClusterResponse> clusterResponses = new ArrayList<ClusterResponse>();
            if (result != null && result.size() > 0) {
                for (Cluster cluster : result) {
                    ClusterResponse clusterResponse = _responseGenerator.createClusterResponse(cluster, false);
                    clusterResponses.add(clusterResponse);
                }
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to add cluster");
            }

            response.setResponses(clusterResponses);
            response.setResponseName(getCommandName());

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
        }
    }

};