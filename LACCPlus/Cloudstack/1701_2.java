//,temp,VolumeOrchestrator.java,1529,1537,temp,VpcManagerImpl.java,1772,1785
//,3
public class xxx {
                @Override
                public void doInTransactionWithoutResult(final TransactionStatus status) {
                    // don't allow to remove gateway when there are static
                    // routes associated with it
                    final long routeCount = _staticRouteDao.countRoutesByGateway(gatewayVO.getId());
                    if (routeCount > 0) {
                        throw new CloudRuntimeException("Can't delete private gateway " + gatewayVO + " as it has " + routeCount
                                + " static routes applied. Remove the routes first");
                    }

                    gatewayVO.setState(VpcGateway.State.Deleting);
                    _vpcGatewayDao.update(gatewayVO.getId(), gatewayVO);
                    s_logger.debug("Marked gateway " + gatewayVO + " with state " + VpcGateway.State.Deleting);
                }

};