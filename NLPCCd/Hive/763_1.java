//,temp,sample_413.java,2,16,temp,sample_362.java,2,16
//,3
public class xxx {
public void dummy_method(){
openTransaction();
List<MTableColumnPrivilege> mSecurityTabPartList;
if (principalName != null && principalType != null) {
query = pm.newQuery(MTableColumnPrivilege.class, "principalName == t1 && principalType == t2");
query.declareParameters("java.lang.String t1, java.lang.String t2");
mSecurityTabPartList = (List<MTableColumnPrivilege>) query.execute(principalName, principalType.toString());
} else {
query = pm.newQuery(MTableColumnPrivilege.class);
mSecurityTabPartList = (List<MTableColumnPrivilege>) query.execute();
}


log.info("done executing query for listprincipaltablecolumngrantsall");
}

};