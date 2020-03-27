package com.apap.tu07.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tu07.model.FlightModel;
import com.apap.tu07.model.PilotModel;
import com.apap.tu07.repository.FlightDb;

/**
 * FlightServiceImpl
 */
@Service
@Transactional
public class FlightServiceImpl implements FlightService {
    @Autowired
    private FlightDb flightDb;
    
    @Override
    public FlightModel addFlight(FlightModel flight) {
        return flightDb.save(flight);
    }

    @Override
    public void deleteByFlightNumber(String flightNumber) {
        flightDb.deleteByFlightNumber(flightNumber);
    }

    @Override
    public Optional<FlightModel> getFlightDetailByFlightNumber(String flightNumber) {
        return flightDb.findByFlightNumber(flightNumber);
    }
    @Override
	public Optional<FlightModel> getFlightDetailById(long id) {
		return flightDb.findById(id);
	}
    @Override
    public List<FlightModel> getAllFlight() {
        return flightDb.findAll();
    }
    
    
    @Override
    public void deleteByFlightId(long id) {
        flightDb.deleteById(id);
    }
    
    @Override
    public FlightModel updateFlight(long id, FlightModel flight) {
    	 FlightModel lastFlight= flightDb.getOne(id);
    	 return flightDb.save(flight);

    }

	
    
    
}