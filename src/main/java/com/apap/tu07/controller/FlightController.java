package com.apap.tu07.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.apap.tu07.model.FlightModel;
import com.apap.tu07.model.PilotModel;
import com.apap.tu07.rest.AmandeusData;
import com.apap.tu07.rest.Setting;
import com.apap.tu07.rest.SettingAmandeus;
import com.apap.tu07.service.FlightService;
import com.apap.tu07.service.PilotService;

/**
 * FlightController
 */
@RestController
@RequestMapping("/flight")
public class FlightController {
    @Autowired
    private FlightService flightService;
    
    @Autowired
    private PilotService pilotService;
    
    @Autowired
    private RestTemplate resttTemplate;
    
    
    @Bean
    public RestTemplate restt() {
    	return new RestTemplate();
    }

    /*
    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
    private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
        PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber).get();
        pilot.setListFlight(new ArrayList<FlightModel>(){
            private ArrayList<FlightModel> init(){
                this.add(new FlightModel());
                return this;
            }
        }.init());

        model.addAttribute("pilot", pilot);
        return "add-flight";
    }

    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"addRow"})
    private String addRow(@ModelAttribute PilotModel pilot, Model model) {
        pilot.getListFlight().add(new FlightModel());
        model.addAttribute("pilot", pilot);
        return "add-flight";
    }

    @RequestMapping(value="/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"removeRow"})
    public String removeRow(@ModelAttribute PilotModel pilot, Model model, HttpServletRequest req) {
        Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        pilot.getListFlight().remove(rowId.intValue());
        
        model.addAttribute("pilot", pilot);
        return "add-flight";
    }

    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"save"})
    private String addFlightSubmit(@ModelAttribute PilotModel pilot) {
        PilotModel archive = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber()).get();
        for (FlightModel flight : pilot.getListFlight()) {
            if (flight != null) {
                flight.setPilot(archive);
                flightService.addFlight(flight);
            }
        }
        return "add";
    }


    @RequestMapping(value = "/flight/view", method = RequestMethod.GET)
    private @ResponseBody FlightModel view(@RequestParam(value = "flightNumber") String flightNumber, Model model) {
        FlightModel archive = flightService.getFlightDetailByFlightNumber(flightNumber).get();
        return archive;
    }

    @RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
    private String delete(@ModelAttribute PilotModel pilot, Model model) {
        for (FlightModel flight : pilot.getListFlight()) {
            flightService.deleteByFlightNumber(flight.getFlightNumber());
        }
        return "delete";
    }

    @RequestMapping(value = "/flight/update", method = RequestMethod.GET)
    private String update(@RequestParam(value = "flightNumber") String flightNumber, Model model) {
        FlightModel archive = flightService.getFlightDetailByFlightNumber(flightNumber).get();
        model.addAttribute("flight", archive);
        return "update-flight";
    }

    @RequestMapping(value = "/flight/update", method = RequestMethod.POST)
    private @ResponseBody FlightModel updateFlightSubmit(@ModelAttribute FlightModel flight, Model model) {
        flightService.addFlight(flight);
        return flight;
    }
    */
    @PostMapping(value = "/add")
    public FlightModel addFlightSubmit(@RequestBody FlightModel flight) {
    	return flightService.addFlight(flight);
    	
    }
    
    @PutMapping(value = "/update/{flightId}")
    public String updatePilotSubmit(@PathVariable("flightId") long flightId,
    		@RequestParam(value = "origin", required=false) String origin,
    		@RequestParam(value = "destination", required=false) String destination,
    		@RequestParam(value = "time", required=false) Date time
    		) {
   FlightModel flight = flightService.getFlightDetailById(flightId).get();
    		
    if(flight.equals(null)) {
    	return "Couldn't find your flight";
    }
    
    if(origin != null) {
    	flight.setOrigin(origin);
    }
    if(time  != null) {
    	flight.setTime(time);
    }
    if(destination  != null) {
    	 flight.setDestination(destination);
    }  
    
    flightService.updateFlight(flightId, flight);
    return "flight update success";
    }
    
    @GetMapping(value = "/view/{flightNumber}")
    public FlightModel flightView(@PathVariable("flightNumber") String flightNumber) {
    	FlightModel flight = flightService.getFlightDetailByFlightNumber(flightNumber).get();
    	return flight;
    }
    
    @GetMapping(value = "/all")
    public List<FlightModel> flightViewAll(Model model) {
    	List<FlightModel> flight = flightService.getAllFlight();
    	return flight;
    }
    
    @DeleteMapping(value = "/delete")
    public String deleteFlight(@RequestParam("flightId") long flightId) {
    	flightService.deleteByFlightId(flightId);	
    	return "flight has been deleted";
    }
    
    @GetMapping(value = "/cariAirport/{kota}")
    public ResponseEntity<String> getStatus(@PathVariable("kota") String kota) throws Exception{
    	//setelah di test tidak ternyata tidak bisa mendapatkan kota di Indonesia, sehingga menggunakan negara Inggris
    	String path = SettingAmandeus.airportUrl + "&keyword="+kota+"&countryCode=GB&sort=analytics.travelers.score&view=FULL" ;
		ResponseEntity<String> entity  = resttTemplate.getForEntity(path, String.class);
    	return entity;
    	
    }
}