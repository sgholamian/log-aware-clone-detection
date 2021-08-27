//,temp,sample_1036.java,2,10,temp,sample_3424.java,2,8
//,3
public class xxx {
public Object process(Node nd, Stack<Node> stack, NodeProcessorCtx procCtx, Object... nodeOutputs) throws SemanticException {
BucketingSortingCtx bctx = (BucketingSortingCtx)procCtx;
FileSinkOperator fop = (FileSinkOperator)nd;
if (fop.getConf().isMmTable()) {


log.info("currently inferring buckets is not going to work for mm tables by design");
}
}

};