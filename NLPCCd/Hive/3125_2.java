//,temp,sample_5239.java,2,18,temp,sample_5238.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (!tbl.getTableType().equals(TableType.EXTERNAL_TABLE.toString())) {
try {
destPath = wh.getPartitionPath(msdb.getDatabase(dbname), tbl, new_part.getValues());
destPath = constructRenamedPath(destPath, new Path(new_part.getSd().getLocation()));
} catch (NoSuchObjectException e) {
throw new InvalidOperationException( "Unable to change partition or table. Database " + dbname + " does not exist" + " Check metastore logs for detailed stack." + e.getMessage());
}
if (destPath != null) {
newPartLoc = destPath.toString();
oldPartLoc = oldPart.getSd().getLocation();


log.info("srcpath");
}
}
}

};