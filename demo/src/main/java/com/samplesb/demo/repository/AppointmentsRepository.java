package com.samplesb.demo.repository;

import com.samplesb.demo.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

public interface AppointmentsRepository extends CrudRepository<Appointment, Integer> {

    public Appointment findByPatientName(String patientName);

    public Appointment findByPatientNameAndAppointmentDate(String patientName, Date appointmentDate);

}
