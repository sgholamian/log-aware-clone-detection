//,temp,ResourceEstimatorService.java,205,214,temp,ResourceEstimatorService.java,182,195
//,3
public class xxx {
  @GET @Path("/skylinestore/estimation/{pipelineId}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getEstimatedResourceAllocation(
      @PathParam("pipelineId") String pipelineId) throws SkylineStoreException {
    RLESparseResourceAllocation result = skylineStore.getEstimation(pipelineId);
    final String skyline = gson.toJson(result, rleType);
    LOGGER.debug("Query the skyline store for pipelineId: {}." + pipelineId);

    return skyline;
  }

};