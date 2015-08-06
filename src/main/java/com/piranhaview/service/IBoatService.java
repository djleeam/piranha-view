package com.piranhaview.service;

import java.util.List;

import com.piranhaview.domain.Boat;

public interface IBoatService {

	Boat create(Boat boat);

	Boat findOne(Long id);

	List<Boat> findAll();

	void save(Boat boat);

	void removeOne(Long id);

}
