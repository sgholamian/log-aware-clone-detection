//,temp,sample_2432.java,2,17,temp,sample_8566.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (s_logger.isDebugEnabled()) {
}
if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
return new Pair<Boolean, ActionOnFailedAuthentication>(false, null);
}
UserAccount user = _userAccountDao.getUserAccount(username, domainId);
if (user == null) {
return new Pair<Boolean, ActionOnFailedAuthentication>(false, null);
}
if (!user.getPassword().equals(password)) {


log.info("password does not match");
}
}

};