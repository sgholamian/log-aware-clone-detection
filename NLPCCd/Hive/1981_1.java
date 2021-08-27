//,temp,sample_4533.java,2,9,temp,sample_4532.java,2,9
//,2
public class xxx {
public void testSimpleOperation() throws Exception {
String typeString = "struct<name:string,studentid:int," + "contact:struct<phNo:string,email:string>," + "currently_registered_courses:array<string>," + "current_grades:map<string,string>," + "phNos:array<struct<phNo:string,type:string>>,blah:array<int>>";
TypeInfo ti = TypeInfoUtils.getTypeInfoFromTypeString(typeString);
HCatSchema hsch = HCatSchemaUtils.getHCatSchemaFromTypeString(typeString);


log.info("hcatschema");
}

};