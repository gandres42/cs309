package edu.demo3.Club;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DataRetrievalFailureException;

import edu.demo3.Annoucement.Annoucement;
import edu.demo3.Annoucement.AnnoucementRepository;
import edu.demo3.Meeting.Meeting;
import edu.demo3.Meeting.MeetingRepository;
import edu.demo3.Document.*;
import edu.demo3.Permissions.UserPermission;
import edu.demo3.User.*;
import edu.demo3.Category.Category;
import edu.demo3.Category.CategoryService;
import java.util.Set;



@RestController
public class ClubController {
    @Autowired
    private ClubService clubService;
    
    @Autowired
    private AnnoucementRepository annoucementRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private DocumentRepository documentRepository;
    
	@Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "Create a new club")
    @PutMapping(path="/clubs/create")
    public @ResponseBody Club addNewclub (@RequestParam String name, @RequestParam String description, @RequestParam int actorId) {    
        User actor = userService.findByStudentid(actorId);
        if (actor == null)
        {
            throw new DataRetrievalFailureException("actor does not exist");
        }
        if (actor.getPermissionlevel() != UserPermission.CLUB_BOARD)
        {
            throw new DataRetrievalFailureException("user is not part of the club board, insufficient permission");
        }
        

        Club newClub = new Club(name, description);
        clubService.save(newClub);
        return newClub;
    }

    @ApiOperation(value = "Create a new subchat")
    @PutMapping(path="/clubs/create_subchat")
    public @ResponseBody void createSubchat (@RequestParam int clubid, @RequestParam String subchatname) {        
        Club club = clubService.findById(clubid);
        int maxid = 0;

        if (club.getSubchats() == null)
        {
            club.setSubchats(new HashMap<Integer, String>());
        }

        if (club.getSubchats().keySet().size() != 0)
        {
            maxid = Collections.max(club.getSubchats().keySet());
        }
        club.getSubchats().put(maxid + 1, subchatname);
        clubService.save(club);
    }

    @ApiOperation(value = "Get all created clubs")
    @GetMapping(path="/clubs/all")
    public @ResponseBody Iterable<Club> getAllClubs() 
    {
        return clubService.findAll();
    }

    @ApiOperation(value = "Get all created clubs")
    @GetMapping(path="/clubs/subchats_by_club")
    public @ResponseBody HashMap<Integer, String> getAllSubchats(@RequestParam int clubid) 
    {
        return clubService.findById(clubid).getSubchats();
    }

    @ApiOperation(value = "Get all members of a given club")
    @GetMapping(path="/clubs/get_users")
    public @ResponseBody Iterable<User> getAllMembers(@RequestParam int clubid) 
    {
        return clubService.findById(clubid).users;
    }

    @ApiOperation(value = "Delete an existing club")
    @DeleteMapping(path="/clubs/delete")
    public @ResponseBody Club deleteClub(@RequestParam int clubid, @RequestParam int actorId)
    {
        User actor = userService.findByStudentid(actorId);
        if (actor == null)
        {
            throw new DataRetrievalFailureException("actor does not exist");
        }
        if (actor.getPermissionlevel() != UserPermission.CLUB_BOARD)
        {
            throw new DataRetrievalFailureException("user is not part of the club board, insufficient permission");
        }
        Club goodbye_world = clubService.findById(clubid);
        if (goodbye_world == null)
        {
            throw new DataRetrievalFailureException("club does not exist");
        }
        clubService.deleteById(clubid);
        return goodbye_world;
    }

    @ApiOperation(value = "Find a club by id")
    @GetMapping(path="/clubs/find_by_id")
    public @ResponseBody Club findClub(@RequestParam int clubid)
    {
        Club club = clubService.findById(clubid);
        if (club == null)
        {
            throw new DataRetrievalFailureException("club does not exist");
        }
        return club;
    }

    @ApiOperation(value = "Search club by name, will find any clubs with names that contain the string given")
    @GetMapping(path="/clubs/search_by_name")
    public @ResponseBody List<Club> clubSearch(@RequestParam String name)
    {
        return clubService.findByNameContaining(name);
    }

    @ApiOperation(value = "Create a new annoucement for a club based on clubid, date is auto-generated on creation")
    @PutMapping(path="/clubs/create_annoucement")
    public @ResponseBody Annoucement createAnnoucement(@RequestParam int clubid, @RequestParam String title, @RequestParam String text)
    {
        Annoucement annoucement = new Annoucement(title, text, clubService.findById(clubid));
        return annoucementRepository.save(annoucement);
    }

    @ApiOperation(value = "Delete an annoucement by annoucementid")
    @DeleteMapping(path="/clubs/delete_annoucement")
    public @ResponseBody void deleteAnnoucement(@RequestParam int annoucementid)
    {
        Annoucement annoucement = annoucementRepository.findById(annoucementid).get();
        annoucement.getClub().getAnnoucements().remove(annoucement);
        annoucementRepository.deleteById(annoucementid);
    }

    @ApiOperation(value = "Create a new meeting for a club based on clubid")
    @PutMapping(path="/clubs/create_meeting")
    public @ResponseBody Meeting createMeeting(@RequestParam String location, @RequestParam String title, @RequestParam int clubid, @RequestParam int year, @RequestParam int month, @RequestParam int day)
    {
        Meeting meeting = new Meeting(location, title, clubService.findById(clubid), year, month, day);
        return meetingRepository.save(meeting);
    }

    @ApiOperation(value = "Delete a meeting by meetingid")
    @DeleteMapping(path="/clubs/delete_meeting")
    public @ResponseBody void deleteMeeting(@RequestParam int meetingid)
    {
        Meeting meeting = meetingRepository.findById(meetingid).get();
        meeting.getClub().getMeetings().remove(meeting);
        meetingRepository.deleteById(meetingid);
    }

    @ApiOperation(value = "Create a new document for a club based on clubid")
    @PutMapping(path="/clubs/create_document")
    public @ResponseBody Document createDocument(@RequestParam String filename, @RequestParam String body, @RequestParam int clubid)
    {
        Document document = new Document(filename, body, clubService.findById(clubid));
        return documentRepository.save(document);
    }

    @ApiOperation(value = "Delete a document by documentid")
    @DeleteMapping(path="/clubs/delete_document")
    public @ResponseBody void deleteDocument(@RequestParam int documentid)
    {
        Document document = documentRepository.findById(documentid).get();
        document.getClub().getDocuments().remove(document);
        documentRepository.deleteById(documentid);
    }

    @ApiOperation(value = "Set the category of a club")
    @PutMapping(path = "/clubs/set_category")
    public @ResponseBody Club setCategory (@RequestParam int clubid, @RequestParam int categoryid)
     {
        Category category = categoryService.findById(categoryid);
        Club club = clubService.findById(clubid);
        if (category == null)
        {
            throw new DataRetrievalFailureException("category does not exist");
        }
        if (club == null)
        {
            throw new DataRetrievalFailureException("club does not exist");
        }

        Set<Club> newClubs = category.getClubs();
        newClubs.add(club);
        category.setClubs(newClubs);
        categoryService.save(category);
        return club;
    }
}
