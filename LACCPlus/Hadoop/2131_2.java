//,temp,AlignedPlannerWithGreedy.java,113,121,temp,GreedyReservationAgent.java,87,95
//,2
public class xxx {
  @Override
  public boolean deleteReservation(ReservationId reservationId, String user,
      Plan plan) throws PlanningException {

    LOG.info("removing the following ReservationId: " + reservationId);

    return planner.deleteReservation(reservationId, user, plan);

  }

};