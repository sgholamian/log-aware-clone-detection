//,temp,AlignedPlannerWithGreedy.java,103,111,temp,GreedyReservationAgent.java,77,85
//,2
public class xxx {
  @Override
  public boolean updateReservation(ReservationId reservationId, String user,
      Plan plan, ReservationDefinition contract) throws PlanningException {

    LOG.info("updating the following ReservationRequest: " + contract);

    return planner.updateReservation(reservationId, user, plan, contract);

  }

};