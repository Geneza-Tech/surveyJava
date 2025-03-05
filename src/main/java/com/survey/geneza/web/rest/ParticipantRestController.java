package com.survey.geneza.web.rest; 
import com.survey.geneza.domain.Participant;
import com.survey.geneza.persistence.ParticipantRepository;
import com.survey.geneza.service.ParticipantService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;


@Controller("ParticipantRestController")
public class ParticipantRestController {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ParticipantService participantService;

    @RequestMapping(value = "/Participant", method = RequestMethod.PUT)
    @ResponseBody
    public Participant saveParticipant(@RequestBody Participant participant) {
    participantService.saveParticipant(participant);
        return participantRepository.findById(participant.getId());
    }

    @RequestMapping(value = "/Participant", method = RequestMethod.POST)
    @ResponseBody
    public Participant newParticipant(@RequestBody Participant participant) {
    participantService.saveParticipant(participant);
        return participantRepository.findById(participant.getId());
    }

    @RequestMapping(value = "/Participant", method = RequestMethod.GET)
    @ResponseBody
    public List<Participant> listParticipants() {
        return new java.util.ArrayList<Participant>(participantService.findAll());
    }

    @RequestMapping(value = "/Participant/{participant_id}", method = RequestMethod.GET)
    @ResponseBody
    public Participant loadParticipant(@PathVariable Integer participant_id) {
        return participantService.findById(participant_id);
    }

    @RequestMapping(value = "/Participant/Delete/{participant_id}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean deleteParticipant(@PathVariable Integer participant_id) {
        return participantService.deleteParticipant(participant_id);
    }
    @RequestMapping(value = "/Participant/Page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Participant> findAllPaged(@PathVariable Integer page){
    	Sort sort = new Sort(new Sort.Order(Direction.DESC, "id"));
		Pageable pageable = new PageRequest(page, 5, sort);
    	return (participantRepository.findAll(pageable));
    }

 @RequestMapping(value = "/Participant/Page/{page}/Sort/{sortField}/Direction/{direction}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Participant> findAllPagedSorted(@PathVariable Integer page, @PathVariable String sortField, @PathVariable int direction){
				
		Sort sort;
		
		if(direction ==1)
			sort = Sort.by(sortField).descending();
		else
			sort = Sort.by(sortField).ascending();
		
		Pageable sortedPaged = PageRequest.of(page, 10, sort);
    	return (participantRepository.findAll(sortedPaged));
    }


    @RequestMapping(value = "/Participant/Person/{person_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Participant> getAllByPersonId(@PathVariable("person_id") Integer personId) {
        return new java.util.ArrayList<Participant>(participantService.findAllByPersonId(personId));
    }

}