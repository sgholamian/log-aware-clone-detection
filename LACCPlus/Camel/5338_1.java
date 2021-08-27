//,temp,CassandraAggregationRepository.java,226,232,temp,CassandraAggregationRepository.java,196,201
//,3
public class xxx {
    private void initDeleteIfIdStatement() {
        Delete delete = generateDelete(table, pkColumns, false);
        Delete deleteIf = delete.ifColumn(exchangeIdColumn).isEqualTo(bindMarker());
        SimpleStatement statement = applyConsistencyLevel(deleteIf.build(), writeConsistencyLevel);
        LOGGER.debug("Generated Delete If Id {}", statement);
        deleteIfIdStatement = getSession().prepare(statement);
    }

};