package st.ilu.rms4csw.model.reservation;

import javax.persistence.Entity;

/**
 * @author Mischa Holz
 */
@Entity
public class RoomReservation extends Reservation<RoomReservation> {

    private boolean approved;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public boolean intersects(RoomReservation other) {
        return this.getTimeSpan().intersects(other.getTimeSpan());
    }
}