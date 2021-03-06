//,temp,sample_533.java,2,15,temp,sample_530.java,2,15
//,2
public class xxx {
public void testDropKeyWhenKeysAreForeignKeys() throws Exception {
when(connectionMock.prepareStatement(contains("DROP FOREIGN KEY"))).thenReturn(preparedStatementMock);
Connection conn = connectionMock;
String tableName = "tableName";
String key = "key";
boolean isForeignKey = true;
dao.dropKey(conn, tableName, key, isForeignKey);
verify(connectionMock, times(1)).prepareStatement(anyString());
verify(preparedStatementMock, times(1)).executeUpdate();
verify(preparedStatementMock, times(1)).close();


log.info("successfully");
}

};