package com.piranhaview.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/api/timeslots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @Autowired
    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<Resource<TimeSlot>> create(@RequestBody @Valid TimeSlot entity) {
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

    @RequestMapping(value = "/search/findByStartTime", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resources<Resource<TimeSlot>>> findByStartTime(@RequestParam("date") String startTime) {
        List<TimeSlot> timeSlots = timeSlotService.findByStartTime(startTime);
        
        List<Resource<TimeSlot>> resources = new ArrayList<Resource<TimeSlot>>();
        for (TimeSlot timeSlot : timeSlots) {
            Resource<TimeSlot> resource = new Resource<TimeSlot>(timeSlot);
            resource.add(linkTo(methodOn(TimeSlotController.class).findOne(timeSlot.getId())).withSelfRel());
            resources.add(resource);
        }

        return new HttpEntity<Resources<Resource<TimeSlot>>>(new Resources<Resource<TimeSlot>>(resources));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable Long id) {
        timeSlotService.removeOne(id);
    }
}
