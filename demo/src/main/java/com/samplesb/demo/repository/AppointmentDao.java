package com.samplesb.demo.repository;

import com.samplesb.demo.Appointment;

import java.util.List;

public interface AppointmentDao {

    public List<Appointment> searchAppointment();

    public Appointment createAppointment(Appointment appointment);

    public Appointment updateAppointment(int id, Appointment appointment);

    public Appointment retrieveAppoitment(int id);

    public boolean deleteAppointment(int id);

    public Appointment retrieveAppointmentByName(String name);


}
