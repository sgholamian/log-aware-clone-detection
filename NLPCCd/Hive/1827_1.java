//,temp,sample_405.java,2,16,temp,sample_389.java,2,16
//,3
public class xxx {
public void dummy_method(){
openTransaction();
List<MPartitionPrivilege> mSecurityTabPartList;
if (principalName != null && principalType != null) {
query = pm.newQuery(MPartitionPrivilege.class, "principalName == t1 && principalType == t2");
query.declareParameters("java.lang.String t1, java.lang.String t2");
mSecurityTabPartList = (List<MPartitionPrivilege>) query.execute(principalName, principalType.toString());
} else {
query = pm.newQuery(MPartitionPrivilege.class);
mSecurityTabPartList = (List<MPartitionPrivilege>) query.execute();
}


log.info("done executing query for listprincipalpartitiongrantsall");
}

};