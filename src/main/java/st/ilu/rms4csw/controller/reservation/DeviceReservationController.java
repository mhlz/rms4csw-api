package st.ilu.rms4csw.controller.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import st.ilu.rms4csw.controller.base.CrudController;
import st.ilu.rms4csw.model.reservation.DeviceReservation;
import st.ilu.rms4csw.repository.reservation.DeviceReservationRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Mischa Holz
 */
@RestController
@RequestMapping("/api/v1/" + DeviceReservationController.API_BASE)
public class DeviceReservationController extends CrudController<DeviceReservation> {

    public static final String API_BASE = "devicereservations";

    @Override
    @RequestMapping
    public List<DeviceReservation> findAll(HttpServletRequest request) {
        return super.findAll(request);
    }

    @Override
    @RequestMapping("/{id}")
    public DeviceReservation findOne(String id) {
        return super.findOne(id);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DeviceReservation> post(DeviceReservation newEntity, HttpServletResponse response) {
        return super.post(newEntity, response);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DeviceReservation put(String id, DeviceReservation entity) {
        return super.put(id, entity);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) {
        return super.delete(id);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public DeviceReservation patch(@PathVariable String id, @RequestBody DeviceReservation entity) {
        return super.patch(id, entity);
    }

    @Override
    public String getApiBase() {
        return API_BASE;
    }

    @Override
    protected Class<DeviceReservation> getEntityClass() {
        return DeviceReservation.class;
    }

    @Autowired
    public void setReservationRepository(DeviceReservationRepository reservationRepository) {
        this.repository = reservationRepository;
    }
}
