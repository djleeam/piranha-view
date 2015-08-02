package com.piranhaview;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService service;

    @Autowired
    public BookingController(BookingService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<Resource<Booking>> create(@RequestBody @Valid Booking entity) {
        Booking booking = service.create(entity);
        Resource<Booking> bookingResource = new Resource<Booking>(booking);
        bookingResource.add(linkTo(methodOn(BookingController.class).findOne(booking.getId())).withSelfRel());

        return new HttpEntity<Resource<Booking>>(bookingResource);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resource<Booking>> findOne(@PathVariable Long id) {
        Booking booking = service.findOne(id);
        Resource<Booking> bookingResource = new Resource<Booking>(booking);
        bookingResource.add(linkTo(methodOn(BookingController.class).findOne(booking.getId())).withSelfRel());

        return new HttpEntity<Resource<Booking>>(bookingResource);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resources<Resource<Booking>>> findAll() {
        List<Booking> bookings = service.findAll();
        
        List<Resource<Booking>> resources = new ArrayList<Resource<Booking>>();
        for (Booking booking : bookings) {
            Resource<Booking> bookingResource = new Resource<Booking>(booking);
            bookingResource.add(linkTo(methodOn(BookingController.class).findOne(booking.getId())).withSelfRel());
            resources.add(bookingResource);
        }

        return new HttpEntity<Resources<Resource<Booking>>>(new Resources<Resource<Booking>>(resources));
    }
}
