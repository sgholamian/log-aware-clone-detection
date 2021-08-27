//,temp,DefaultUnitOfWork.java,279,288,temp,DefaultUnitOfWork.java,268,277
//,2
public class xxx {
    @Override
    public void afterRoute(Exchange exchange, Route route) {
        if (log.isTraceEnabled()) {
            log.trace("UnitOfWork afterRoute: {} for ExchangeId: {} with {}", route.getId(), exchange.getExchangeId(),
                    exchange);
        }
        if (synchronizations != null && !synchronizations.isEmpty()) {
            UnitOfWorkHelper.afterRouteSynchronizations(route, exchange, synchronizations, log);
        }
    }

};