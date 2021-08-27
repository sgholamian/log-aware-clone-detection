//,temp,sample_200.java,2,19,temp,sample_201.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (isAcidTable) {
parseCtx.getGlobalLimitCtx().disableOpt();
} else {
long sizePerRow = HiveConf.getLongVar(parseCtx.getConf(), HiveConf.ConfVars.HIVELIMITMAXROWSIZE);
sizeNeeded = (parseCtx.getGlobalLimitCtx().getGlobalOffset() + parseCtx.getGlobalLimitCtx().getGlobalLimit()) * sizePerRow;
fileLimit = HiveConf.getIntVar(parseCtx.getConf(), HiveConf.ConfVars.HIVELIMITOPTLIMITFILE);
if (sizePerRow <= 0 || fileLimit <= 0) {
parseCtx.getGlobalLimitCtx().disableOpt();
} else if (parts.isEmpty()) {
} else {


log.info("try to reduce input size for limit sizeneeded file limit");
}
}
}

};