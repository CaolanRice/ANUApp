package dev.caolan.godis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class GodisControllerTest {

    @InjectMocks
    private GodisController godisController;

    @Mock
    private GodisService godisService;

    @Mock
    private GodisController controller;

    @Mock
    private MongoTemplate mongoTemplate;


    private MockMvc mockMvc; //spring mvc test framework for testing http request and response without starting server

    @Before //setup method
    public void setUp() {
        //initialize mockmvc object with controller instance to simulate http requests and responses
        mockMvc = MockMvcBuilders.standaloneSetup(godisController)
                .build();
    }

    @Test
    public void testGetAllGodis() throws Exception {
        // Test data and expected behaviour
        List<Godis> godisList = new ArrayList<>();
        godisList.add(new Godis("Godis1", "Type1", 4.5));
        //mock allGodis method, mocked response is created godisList
        Mockito.when(godisService.allGodis()).thenReturn(godisList);

        // Store result of GET request to endpoint in MvcResult object
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/godis"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Compare actual results with expected results
        String response = result.getResponse().getContentAsString();
        List<Godis> responseGodisList = new ObjectMapper().readValue(response, new TypeReference<List<Godis>>() {
        });
        Assert.assertEquals(godisList.size(), responseGodisList.size());
        Assert.assertEquals(godisList.get(0).getName(), responseGodisList.get(0).getName());
    }

    @Test
    public void testGetGodisByRating() throws Exception {
        Double rating = 4.5;
        Godis godis = new Godis("Godis1", "Type1", 4.5);
        Optional<Godis> optionalGodis = Optional.of(godis);

        Mockito.when(godisService.byRating(rating)).thenReturn(optionalGodis);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/godis/rating/{rating}", rating))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Godis responseGodis = new ObjectMapper().readValue(response, Godis.class);
        Assert.assertEquals(godis.getName(), responseGodis.getName());
        Assert.assertEquals(godis.getType(), responseGodis.getType());

    }

    @Test
    public void testGetGodisByType() throws Exception {
        String type = "Type1";
        Godis godis = new Godis("Godis1", "Type1", 4.5);
        List<Godis> listOfGodis = List.of(godis);

        Mockito.when(godisService.byType(type)).thenReturn(listOfGodis);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/godis/type/{type}", type))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Godis[] responseGodis = new ObjectMapper().readValue(response, Godis[].class);
        Assert.assertEquals(godis.getName(), responseGodis[0].getName());
        Assert.assertEquals(godis.getRating(), responseGodis[0].getRating(), 0.001); //delta = margin of error. 0.001 for precision when comparing floating point
    }

    @Test
    public void testDeleteAllGodis() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/godis/delete/all")) //send DELETE request to endpoint
                .andExpect(MockMvcResultMatchers.status().isOk()) //check if http status response is 200 (OK)
                .andReturn(); //return result of request

        verify(godisService, times(1)).deleteAll(); //verify that deleteAll method is called only once
        String response = result.getResponse().getContentAsString(); //get response contest as string
        Assert.assertEquals("All godis deleted", response); //check if expected response content is equal to actual response content
    }


    @Test
    public void testGetGodisByName() throws Exception {
        String encodedGodisName = "Test%20godis";
        String godisName = "Test godis";
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(godisName));
        List<Godis> expectedGodisList = mongoTemplate.find(query, Godis.class);

        List<Godis> actualGodisList = controller.getGodisByName(encodedGodisName);

        assertEquals(expectedGodisList, actualGodisList);
    }


    @Test
    public void testCreateGodis() throws Exception {
        Godis godis = new Godis(null, "Test Godis", "Test Type", 4.5, null);

        //mock godisService object
        GodisService godisService = Mockito.mock(GodisService.class);
        //Create instance of godisController class with mocked object
        GodisController godisController = new GodisController(godisService);

        // call method to be tested
        godisController.createGodis(godis);

        // verify that addGodis is invoked with expected args
        verify(godisService).addGodis(godis);
    }

    @Test
    public void testGetGodisById() {
        ObjectId id = new ObjectId();
        Godis godis = new Godis(id, "Fizzy pigtails", "Sweet", 4.5, Arrays.asList("Sour", "Fizzy"));
        when(godisService.getBy(id)).thenReturn(Optional.of(godis));
        ResponseEntity<Godis> response = godisController.getGodisById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(godis, response.getBody());
    }
//test ci/cd


}
