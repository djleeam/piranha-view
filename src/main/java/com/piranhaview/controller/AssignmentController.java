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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.piranhaview.domain.Assignment;
import com.piranhaview.service.AssignmentService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Api(value = "assignments", description = "Operations for assignments")
@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService service;

    @Autowired
    public AssignmentController(AssignmentService service) {
        this.service = service;
    }

    @ApiOperation(value = "Create an assignment", response = Assignment.class)
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<Resource<Assignment>> create(
    		@ApiParam(value = "Example with required elements:\n"
					+ "<pre><code>{\n"
					+ "  \"timeslot_id\": 1,\n"
					+ "  \"boat_id\": 1\n"
					+ "}</pre></code>", required = true)
    		@RequestBody @Valid Assignment assignment) {
        Assignment created = service.create(assignment);
        Resource<Assignment> resource = new Resource<Assignment>(created);
        resource.add(linkTo(methodOn(AssignmentController.class).findOne(created.getId())).withSelfRel());

        return new HttpEntity<Resource<Assignment>>(resource);
    }

    @ApiOperation(value = "Get an assignment by id", response = Assignment.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resource<Assignment>> findOne(@PathVariable Long id) {
        Assignment assignment = service.findOne(id);

        if (assignment != null) {
	        Resource<Assignment> resource = new Resource<Assignment>(assignment);
	        resource.add(linkTo(methodOn(AssignmentController.class).findOne(assignment.getId())).withSelfRel());
	        return new HttpEntity<Resource<Assignment>>(resource);
        }
        else {
        	return new ResponseEntity<Resource<Assignment>>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Get all assignments", response = Assignment.class, responseContainer = "List")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resources<Resource<Assignment>>> findAll() {
        List<Assignment> assignments = service.findAll();
        
        List<Resource<Assignment>> resources = new ArrayList<Resource<Assignment>>();
        for (Assignment assignment : assignments) {
            Resource<Assignment> resource = new Resource<Assignment>(assignment);
            resource.add(linkTo(methodOn(AssignmentController.class).findOne(assignment.getId())).withSelfRel());
            resources.add(resource);
        }

        return new HttpEntity<Resources<Resource<Assignment>>>(new Resources<Resource<Assignment>>(resources));
    }

    @ApiOperation(value = "Delete an assignment by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable Long id) {
        service.removeOne(id);
    }
}
