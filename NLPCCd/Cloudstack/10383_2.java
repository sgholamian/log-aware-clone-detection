//,temp,sample_8400.java,2,15,temp,sample_8565.java,2,14
//,3
public class xxx {
public Pair<Boolean, ActionOnFailedAuthentication> authenticate(String username, String password, Long domainId, Map<String, Object[]> requestParameters) {
if (s_logger.isDebugEnabled()) {
}
if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
return new Pair<Boolean, ActionOnFailedAuthentication>(false, null);
}
UserAccount user = _userAccountDao.getUserAccount(username, domainId);
if (user == null) {


log.info("unable to find user with in domain");
}
}

};