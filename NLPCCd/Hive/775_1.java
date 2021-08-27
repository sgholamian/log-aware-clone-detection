//,temp,sample_4555.java,2,11,temp,sample_4554.java,2,9
//,3
public class xxx {
public PhysicalContext resolve(PhysicalContext pctx) throws SemanticException {
this.conf = pctx.getConf();
this.mode = LlapMode.valueOf(HiveConf.getVar(conf, HiveConf.ConfVars.LLAP_EXECUTION_MODE));
Preconditions.checkState(this.mode != null, "Unrecognized LLAP mode configuration: " + HiveConf.getVar(conf, HiveConf.ConfVars.LLAP_EXECUTION_MODE));
if (mode == none) {


log.info("llap disabled");
}
}

};