//,temp,GenericUDAFExceptionInVertex.java,107,119,temp,GenericUDFExceptionInVertex.java,122,134
//,2
public class xxx {
  @Override
  public void configure(MapredContext mapredContext) {
    this.currentVertexName = mapredContext.getJobConf().get(TezProcessor.HIVE_TEZ_VERTEX_NAME);
    this.currentTaskNumber =
        mapredContext.getJobConf().getInt(TezProcessor.HIVE_TEZ_TASK_INDEX, -1);
    this.currentTaskAttemptNumber =
        mapredContext.getJobConf().getInt(TezProcessor.HIVE_TEZ_TASK_ATTEMPT_NUMBER, -1);

    LOG.debug(
        "configure vertex: {}, task: {}, attempt: {} <-> current vertex {}, task: {}, attempt: {}",
        vertexName, taskNumberExpr, taskAttemptNumberExpr, currentVertexName, currentTaskNumber,
        currentTaskAttemptNumber);
  }

};