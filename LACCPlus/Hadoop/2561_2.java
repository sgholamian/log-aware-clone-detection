//,temp,ResourceEstimatorService.java,205,214,temp,ResourceEstimatorService.java,182,195
//,3
public class xxx {
  @GET @Path("/skylinestore/history/{pipelineId}/{runId}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getHistoryResourceSkyline(
      @PathParam("pipelineId") String pipelineId,
      @PathParam("runId") String runId) throws SkylineStoreException {
    RecurrenceId recurrenceId = new RecurrenceId(pipelineId, runId);
    Map<RecurrenceId, List<ResourceSkyline>> jobHistory =
        skylineStore.getHistory(recurrenceId);
    final String skyline = gson.toJson(jobHistory, skylineStoreType);
    LOGGER
        .debug("Query the skyline store for recurrenceId: {}." + recurrenceId);

    return skyline;
  }

};