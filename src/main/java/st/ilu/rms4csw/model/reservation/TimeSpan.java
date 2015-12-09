package st.ilu.rms4csw.model.reservation;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

/**
 * @author Mischa Holz
 */
@Embeddable
public class TimeSpan {

    @Column(name = "beginning_timestamp")
    private Date beginning;

    @Column(name = "end_timestamp")
    private Date end;

    public TimeSpan() {
    }

    public TimeSpan(Date beginning, Date end) {
        this.beginning = beginning;
        this.end = end;
    }

    public Date getBeginning() {
        return beginning;
    }

    public void setBeginning(Date beginning) {
        this.beginning = beginning;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public boolean intersects(TimeSpan other) {
        TimeSpan one = this;
        TimeSpan two = other;

        return checkIntersectInOneDirection(one, two) || checkIntersectInOneDirection(two, one);
    }

    private boolean checkIntersectInOneDirection(TimeSpan one, TimeSpan two) {
        long unixStartOne = one.beginning.getTime();
        long unixEndOne = one.end.getTime();

        long unixStartTwo = two.beginning.getTime();
        long unixEndTwo = two.end.getTime();

        if(unixStartOne >= unixStartTwo && unixStartOne <= unixEndTwo) {
            return true;
        }

        if(unixEndOne <= unixEndTwo && unixEndOne >= unixStartTwo) {
            return true;
        }

        return false;
    }
}