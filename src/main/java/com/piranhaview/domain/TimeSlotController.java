package com.piranhaview.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.piranhaview.service.TimeSlotService;

@RestController
@RequestMapping("/api/timeslots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @Autowired
    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<Resource<TimeSlot>> createForm(@Valid TimeSlot entity) {
    	return createTimeSlot(entity);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<Resource<TimeSlot>> createJson(@RequestBody @Valid TimeSlot entity) {
    	return createTimeSlot(entity);
    }

	private HttpEntity<Resource<TimeSlot>> createTimeSlot(TimeSlot entity) {
		TimeSlot timeSlot = timeSlotService.create(entity);
        Resource<TimeSlot> resource = new Resource<TimeSlot>(timeSlot);
        resource.add(linkTo(methodOn(TimeSlotController.class).findOne(timeSlot.getId())).withSelfRel());

        return new HttpEntity<Resource<TimeSlot>>(resource);
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resource<TimeSlot>> findOne(@PathVariable Long id) {
    	TimeSlot timeSlot = timeSlotService.findOne(id);
        Resource<TimeSlot> resource = new Resource<TimeSlot>(timeSlot);
        resource.add(linkTo(methodOn(TimeSlotController.class).findOne(timeSlot.getId())).withSelfRel());

        return new HttpEntity<Resource<TimeSlot>>(resource);
    }

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
}
