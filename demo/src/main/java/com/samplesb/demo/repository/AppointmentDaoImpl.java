package com.samplesb.demo.repository;

import com.samplesb.demo.Appointment;
import com.samplesb.demo.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentDaoImpl implements AppointmentDao {

    @Autowired
    AppointmentsRepository appointmentsRepository;

    @Override
    public List<Appointment> searchAppointment() {
        return null;
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {

     return  appointmentsRepository.save(appointment);

    }

    @Override
    public Appointment updateAppointment(int id, Appointment appointment) {
        return null;
    }

    @Override
    public Appointment retrieveAppoitment(int id) {

        Optional<Appointment> optionalAppointment = appointmentsRepository.findById(Integer.valueOf(id));
        if (optionalAppointment.isPresent()) return optionalAppointment.get();

        return null;
    }

    @Override
    public boolean deleteAppointment(int id) {
        try{
            appointmentsRepository.deleteById(id);
            return true;

        } catch (EmptyResultDataAccessException er) {

            throw new MyException(HttpStatus.NOT_FOUND, "Data is not there with the given ID");
        }

        catch (Exception e){

            return false;
        }


    }

    @Override
    public Appointment retrieveAppointmentByName(String name) {

        appointmentsRepository.findByPatientName(name);
        return null;
    }
}
