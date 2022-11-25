package edu.demo3;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import edu.demo3.Club.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestClubService {

    @InjectMocks
    ClubService clubService;

    @Mock
    ClubRepository repo;

    @Before
    public void init() {
		MockitoAnnotations.openMocks(this);
	}

    @Test
	public void findByIdTest() {
		//userController.createUser(1, "jDoe", "john", "doe", "jDoe@gmail.com", "password");
        
        when(repo.findById(1)).thenReturn(new Club("robotics club", "we are robots"));

		Club club = clubService.findById(1);

		assertEquals("robotics club", club.getName());
		assertEquals("we are robots", club.getDescription());
	}


	@Test
	public void getAllUsersTest() {
		List<Club> list = new ArrayList<Club>();
		Club club1 = new Club("robotics club", "we are robots");
		Club club2 = new Club("cheese club", "we are cheese");
		Club club3 = new Club("mouse club", "we are mice");

		list.add(club1);
		list.add(club2);
		list.add(club3);

		when(repo.findAll()).thenReturn(list);

		Iterable<Club> acctList = clubService.findAll();

		assertEquals(3, ((List<Club>) acctList).size());
		//verify(repo, times(1)).findAll();
	}    
}
