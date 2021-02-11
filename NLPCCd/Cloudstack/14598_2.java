//,temp,sample_537.java,2,15,temp,sample_539.java,2,15
//,2
public class xxx {
public void testDropPrimaryKeyWhenExecuteUpdateResultsInException() throws Exception {
SQLException sqlException = new SQLException();
when(connectionMock.prepareStatement(contains("DROP PRIMARY KEY"))).thenReturn(preparedStatementMock);
when(preparedStatementMock.executeUpdate()).thenThrow(sqlException);
Connection conn = connectionMock;
String tableName = null;
dao.dropPrimaryKey(conn, tableName);
verify(connectionMock, times(1)).prepareStatement(anyString());
verify(preparedStatementMock, times(1)).executeUpdate();
verify(preparedStatementMock, times(1)).close();


log.info("Exception");
}

};