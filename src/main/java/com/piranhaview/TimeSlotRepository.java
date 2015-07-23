package com.piranhaview;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "timeslots", path = "timeslots")
public interface TimeSlotRepository extends PagingAndSortingRepository<TimeSlot, Long> {

	@Query("SELECT t FROM TimeSlot t WHERE FORMATDATETIME(t.startTime,'yyyy-MM-dd') = :date")
	List<TimeSlot> findByStartTime(@Param("date") String date);

	@Query("SELECT t FROM TimeSlot t WHERE t.duration = :duration")
	List<TimeSlot> findByDuration(@Param("duration") Long duration);

}
