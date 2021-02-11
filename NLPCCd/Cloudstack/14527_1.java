//,temp,sample_7522.java,2,11,temp,sample_2027.java,2,11
//,2
public class xxx {
private Answer handleException(NiciraNvpApiException e, ConfigureSharedNetworkUuidCommand command, NiciraNvpResource niciraNvpResource) {
if (HttpStatusCodeHelper.isConflict(e.getErrorCode())){
return new ConfigureSharedNetworkUuidAnswer(command, false, "FAILED: There's been a conflict in NSX side");
}
else {


log.info("error code retrying");
}
}

};