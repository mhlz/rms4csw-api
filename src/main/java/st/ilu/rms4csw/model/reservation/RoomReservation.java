package st.ilu.rms4csw.model.reservation;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;

/**
 * @author Mischa Holz
 */
@Entity
@RoomReservationDoesNotIntersect
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class RoomReservation extends Reservation<RoomReservation> {

    private static final long OPEN_EXPAND_MILLIS = 15 * 60 * 1000;

    private Boolean approved;

    @NotEmpty(message = "Raumbuchungen brauchen einen Titel")
    private String title;

    private Boolean open;

    public Boolean isOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public TimeSpan getOpenedTimeSpan() {
        return this.getTimeSpan().expand(OPEN_EXPAND_MILLIS);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    @Override
    public boolean intersects(RoomReservation other) {
        return this.getTimeSpan().intersects(other.getTimeSpan());
    }
}
