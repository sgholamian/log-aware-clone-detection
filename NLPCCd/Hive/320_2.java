//,temp,sample_2478.java,2,9,temp,sample_2479.java,2,11
//,3
public class xxx {
private void handle(ChannelHandlerContext ctx, JobSubmitted msg) {
JobHandleImpl<?> handle = jobs.get(msg.clientJobId);
if (handle != null) {
handle.addSparkJobId(msg.sparkJobId);
} else {


log.info("received spark job id for unknown job");
}
}

};