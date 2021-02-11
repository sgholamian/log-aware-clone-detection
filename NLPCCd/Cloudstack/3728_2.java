//,temp,sample_535.java,2,16,temp,sample_532.java,2,16
//,2
public class xxx {
public void dummy_method(){
when(connectionMock.prepareStatement(contains("DROP KEY null"))).thenReturn(preparedStatementMock);
when(preparedStatementMock.executeUpdate()).thenThrow(sqlException);
Connection conn = connectionMock;
String tableName = "tableName";
String key = null;
boolean isForeignKey = false;
dao.dropKey(conn, tableName, key, isForeignKey);
verify(connectionMock, times(1)).prepareStatement(anyString());
verify(preparedStatementMock, times(1)).executeUpdate();
verify(preparedStatementMock, times(1)).close();


log.info("Exception");
}

};