//,temp,sample_7490.java,2,15,temp,sample_7489.java,2,14
//,3
public class xxx {
private Record searchRecord(String recordName, Long domainId) {
if (recordName == null || domainId == null) {
return null;
}
List<Record> candidates = _globoDns.getRecordAPI().listByQuery(domainId, recordName);
for (Record candidate : candidates) {
if (recordName.equalsIgnoreCase(candidate.getName())) {
return candidate;
}
}


log.info("record in domain id not found in globodns");
}

};