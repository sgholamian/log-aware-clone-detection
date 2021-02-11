//,temp,sample_7521.java,2,8,temp,sample_2026.java,2,8
//,2
public class xxx {
private Answer handleException(NiciraNvpApiException e, ConfigureSharedNetworkUuidCommand command, NiciraNvpResource niciraNvpResource) {
if (HttpStatusCodeHelper.isConflict(e.getErrorCode())){


log.info("there s been a conflict in nsx side aborting implementation");
}
}

};