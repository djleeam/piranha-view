package com.piranhaview.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.piranhaview.domain.TimeSlot;
import com.piranhaview.service.TimeSlotService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Api(value = "timeslots", description = "Operations for time slots")
@RestController
@RequestMapping("/api/timeslots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @Autowired
    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @ApiOperation(value = "Create a time slot", response = TimeSlot.class)
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<Resource<TimeSlot>> create(
    		@ApiParam(value = "Example with required elements:\n"
    							+ "<pre><code>{\n"
    							+ "  \"start_time\": 1437533075000,\n"
    							+ "  \"duration\": 120\n"
    							+ "}</pre></code>", required = true)
    		@RequestBody @Valid TimeSlot timeSlot) {
        TimeSlot created = timeSlotService.create(timeSlot);
        Resource<TimeSlot> resource = new Resource<TimeSlot>(created);
        resource.add(linkTo(methodOn(TimeSlotController.class).findOne(created.getId())).withSelfRel());

        return new HttpEntity<Resource<TimeSlot>>(resource);
    }

    @ApiOperation(value = "Get a time slot by id", response = TimeSlot.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resource<TimeSlot>> findOne(@PathVariable Long id) {
        TimeSlot timeSlot = timeSlotService.findOne(id);

        if (timeSlot != null) {
	        Resource<TimeSlot> resource = new Resource<TimeSlot>(timeSlot);
	        resource.add(linkTo(methodOn(TimeSlotController.class).findOne(timeSlot.getId())).withSelfRel());
	        return new HttpEntity<Resource<TimeSlot>>(resource);
        }
        else {
        	return new ResponseEntity<Resource<TimeSlot>>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Get all time slots", response = TimeSlot.class, responseContainer = "List")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resources<Resource<TimeSlot>>> findAll() {
        List<TimeSlot> timeSlots = timeSlotService.findAll();
        
        List<Resource<TimeSlot>> resources = new ArrayList<Resource<TimeSlot>>();
        for (TimeSlot timeSlot : timeSlots) {
            Resource<TimeSlot> resource = new Resource<TimeSlot>(timeSlot);
            resource.add(linkTo(methodOn(TimeSlotController.class).findOne(timeSlot.getId())).withSelfRel());
            resources.add(resource);
        }

        return new HttpEntity<Resources<Resource<TimeSlot>>>(new Resources<Resource<TimeSlot>>(resources));
    }

    @ApiOperation(value = "Get time slots by date", response = TimeSlot.class, responseContainer = "List")
    @RequestMapping(value = "/search/findByStartTime", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resources<Resource<TimeSlot>>> findByStartTime(
    		@ApiParam(value = "Example: 2015-07-22", required = true)
    		@RequestParam("date") String startTime) {

        List<TimeSlot> timeSlots = timeSlotService.findByStartTime(startTime);
        
        List<Resource<TimeSlot>> resources = new ArrayList<Resource<TimeSlot>>();
        for (TimeSlot timeSlot : timeSlots) {
            Resource<TimeSlot> resource = new Resource<TimeSlot>(timeSlot);
            resource.add(linkTo(methodOn(TimeSlotController.class).findOne(timeSlot.getId())).withSelfRel());
            resources.add(resource);
        }

        return new HttpEntity<Resources<Resource<TimeSlot>>>(new Resources<Resource<TimeSlot>>(resources));
    }

    @ApiOperation(value = "Delete a time slot by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable Long id) {
        timeSlotService.removeOne(id);
    }
}
