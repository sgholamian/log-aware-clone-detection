//,temp,sample_405.java,2,16,temp,sample_389.java,2,16
//,3
public class xxx {
public void dummy_method(){
openTransaction();
List<MPartitionColumnPrivilege> mSecurityTabPartList;
if (principalName != null && principalType != null) {
query = pm.newQuery(MPartitionColumnPrivilege.class, "principalName == t1 && principalType == t2");
query.declareParameters("java.lang.String t1, java.lang.String t2");
mSecurityTabPartList = (List<MPartitionColumnPrivilege>) query.executeWithArray(principalName, principalType.toString());
} else {
query = pm.newQuery(MPartitionColumnPrivilege.class);
mSecurityTabPartList = (List<MPartitionColumnPrivilege>) query.execute();
}


log.info("done executing query for listprincipalpartitioncolumngrantsall");
}

};