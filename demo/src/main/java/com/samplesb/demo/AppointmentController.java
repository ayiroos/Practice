package com.samplesb.demo;


import com.samplesb.demo.exception.MyException;
import com.samplesb.demo.repository.AppointmentDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AppointmentController {

    public List<Appointment> APPOINTMENTS;

    @Autowired
    AppointmentDao appointmentDao;

    @PostConstruct
    public void init() {

        Appointment a1 = new Appointment(1, "a1", "M", new Date(System.currentTimeMillis()));
        Appointment a2 = new Appointment(2, "a2", "M", new Date(System.currentTimeMillis()));
        Appointment a3 = new Appointment(3, "a3", "F", new Date(System.currentTimeMillis()));
        Appointment a4 = new Appointment(4, "a4", "M", new Date(System.currentTimeMillis()));
        Appointment a5 = new Appointment(5, "a5", "F", new Date(System.currentTimeMillis()));

        APPOINTMENTS = new ArrayList<>();

        APPOINTMENTS.add(a1);
        APPOINTMENTS.add(a2);
        APPOINTMENTS.add(a3);
        APPOINTMENTS.add(a4);
        APPOINTMENTS.add(a5);


    }

    @RequestMapping(value = "/appointments", method = RequestMethod.GET, produces = "application/json")
    public List<Appointment> searchAppointments(@RequestParam(required = false) String gender) {

        if (StringUtils.isNotBlank(gender)) {

            if (!StringUtils.equalsIgnoreCase(gender, "M") || !StringUtils.equalsIgnoreCase(gender, "F")) {
                throw new MyException(HttpStatus.BAD_REQUEST, "please provide M or F for gender");
            }

            return APPOINTMENTS.stream().filter(a -> StringUtils.equalsIgnoreCase(a.getGender(), gender)).collect(Collectors.toList());

        }

        return APPOINTMENTS;
    }

    @RequestMapping(value = "/appointments/{id}", method = RequestMethod.GET, produces = "application/json")
    public Appointment retrieveAppointment(@PathVariable int id) {

//        Optional<Appointment> appointment = APPOINTMENTS.stream().filter(a -> a.getId() == id).findFirst();
//        if (appointment.isPresent()) {
//
//            return appointment.get();
//        }

        Appointment a= appointmentDao.retrieveAppoitment(id);

        if (a== null) {

            throw new MyException(HttpStatus.NOT_FOUND, String.format("The Appointment or the given ID %d not found", id));

        }

        return a;
    }

    @RequestMapping(value = "/appointments", method = RequestMethod.POST, produces = "application/json", consumes =
            "application/json")
    public Appointment createAppointment(@RequestBody Appointment appointment) {

        appointment.setAppointmentDate(new Date(System.currentTimeMillis()));
        appointmentDao.createAppointment(appointment);

        return appointment;

    }

    @RequestMapping(value = "/appointments/{id}", method = RequestMethod.PATCH, produces = "application/json",
            consumes =
                    "application/json")
    public Appointment updateAppointment(@RequestBody Appointment appointment,
                                         @PathVariable int id) {
        Appointment toBeDel = null;

        try {


            for (Appointment a : APPOINTMENTS) {
                if (a.getId() == id) {
                    toBeDel = a;

                }

            }

            if (toBeDel != null) {

                APPOINTMENTS.remove(toBeDel);
            } else {
                throw new MyException(HttpStatus.NOT_FOUND, "Element " + id + " is not found to get deleted");
            }


            APPOINTMENTS.add(appointment);


        } catch (Exception e) {

            throw e;
        }

        return appointment;
    }

    @RequestMapping(value = "/appointments/{id}", method = RequestMethod.DELETE)
    public boolean deleteAppointment(@PathVariable int id) {


        return appointmentDao.deleteAppointment(id);

    }


}
