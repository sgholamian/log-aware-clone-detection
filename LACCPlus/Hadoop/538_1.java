//,temp,AlignedPlannerWithGreedy.java,77,101,temp,GreedyReservationAgent.java,51,75
//,2
public class xxx {
  @Override
  public boolean createReservation(ReservationId reservationId, String user,
      Plan plan, ReservationDefinition contract) throws PlanningException {

    LOG.info("placing the following ReservationRequest: " + contract);

    try {
      boolean res =
          planner.createReservation(reservationId, user, plan, contract);

      if (res) {
        LOG.info("OUTCOME: SUCCESS, Reservation ID: "
            + reservationId.toString() + ", Contract: " + contract.toString());
      } else {
        LOG.info("OUTCOME: FAILURE, Reservation ID: "
            + reservationId.toString() + ", Contract: " + contract.toString());
      }
      return res;
    } catch (PlanningException e) {
      LOG.info("OUTCOME: FAILURE, Reservation ID: " + reservationId.toString()
          + ", Contract: " + contract.toString());
      throw e;
    }

  }

};