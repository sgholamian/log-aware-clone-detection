//,temp,sample_7854.java,2,17,temp,sample_2891.java,2,17
//,2
public class xxx {
public EngineDataCenterVO findByTokenOrIdOrName(String tokenOrIdOrName) {
EngineDataCenterVO result = findByToken(tokenOrIdOrName);
if (result == null) {
result = findByName(tokenOrIdOrName);
if (result == null) {
try {
Long dcId = Long.parseLong(tokenOrIdOrName);
return findById(dcId);
} catch (NumberFormatException nfe) {


log.info("cannot parse into long");
}
}
}
}

};