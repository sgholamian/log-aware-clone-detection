//,temp,sample_3631.java,2,16,temp,sample_3630.java,2,17
//,3
public class xxx {
public void dummy_method(){
pStmt = dbConn.prepareStatement(s);
paramCount = 1;
pStmt.setString(paramCount++, info.dbname);
pStmt.setString(paramCount++, info.tableName);
if(info.highestTxnId != 0) {
pStmt.setLong(paramCount++, info.highestTxnId);
}
if (info.partName != null) {
pStmt.setString(paramCount++, info.partName);
}


log.info("going to execute update");
}

};