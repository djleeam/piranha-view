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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.piranhaview.domain.Booking;
import com.piranhaview.service.BookingService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<Resource<Booking>> create(@RequestBody @Valid Booking entity) {
        Booking booking = bookingService.create(entity);
        Resource<Booking> resource = new Resource<Booking>(booking);
        resource.add(linkTo(methodOn(BookingController.class).findOne(booking.getId())).withSelfRel());

        return new HttpEntity<Resource<Booking>>(resource);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resource<Booking>> findOne(@PathVariable Long id) {
        Booking booking = bookingService.findOne(id);
        Resource<Booking> resource = new Resource<Booking>(booking);
        resource.add(linkTo(methodOn(BookingController.class).findOne(booking.getId())).withSelfRel());

        return new HttpEntity<Resource<Booking>>(resource);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resources<Resource<Booking>>> findAll() {
        List<Booking> bookings = bookingService.findAll();
        
        List<Resource<Booking>> resources = new ArrayList<Resource<Booking>>();
        for (Booking booking : bookings) {
            Resource<Booking> resource = new Resource<Booking>(booking);
            resource.add(linkTo(methodOn(BookingController.class).findOne(booking.getId())).withSelfRel());
            resources.add(resource);
        }

        return new HttpEntity<Resources<Resource<Booking>>>(new Resources<Resource<Booking>>(resources));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable Long id) {
        bookingService.removeOne(id);
    }
}
