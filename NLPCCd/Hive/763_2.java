//,temp,sample_413.java,2,16,temp,sample_362.java,2,16
//,3
public class xxx {
public void dummy_method(){
if (principalName != null && principalType != null) {
query = queryWrapper.query = pm.newQuery(MDBPrivilege.class, "principalName == t1 && principalType == t2");
query.declareParameters("java.lang.String t1, java.lang.String t2");
mSecurityDBList = (List<MDBPrivilege>) query.execute(principalName, principalType.toString());
} else {
query = queryWrapper.query = pm.newQuery(MDBPrivilege.class);
mSecurityDBList = (List<MDBPrivilege>) query.execute();
}
pm.retrieveAll(mSecurityDBList);
success = commitTransaction();


log.info("done retrieving all objects for listprincipalalldbgrant");
}

};