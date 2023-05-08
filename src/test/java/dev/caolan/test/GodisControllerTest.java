
package dev.caolan.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Criteria;
import dev.caolan.godis.Godis;
import dev.caolan.godis.GodisController;
import dev.caolan.godis.GodisService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.data.mongodb.core.query.Query;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GodisControllerTest {

    private static GodisService godisService = mock(GodisService.class);
    private static GodisController godisController = new GodisController(godisService);
    private static MockMvc mockMvc;
    private static MongoTemplate mongoTemplate;

    @BeforeAll //setup method
    public static void setUp() {
        //initialize mockmvc object with controller instance to simulate http requests and responses
        mockMvc = MockMvcBuilders.standaloneSetup(godisController)
                .build();
    }

    @Test
    public void testGetAllGodis() {
        // Test data and expected behaviour
        List<Godis> godisList = new ArrayList<>();
        Godis godis1 = new Godis("Godis1", "Type1", 4.5);;
        godisList.add(godis1);

        //mock allGodis method, mocked response is created godisList
        when(godisService.allGodis()).thenReturn(godisList);

        // Call of getAllGodis method should return object containing list of godis objects
        ResponseEntity<List<Godis>> response = godisController.getAllGodis();


        // Asserttions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(godisList, response.getBody());

        // verify that the godisService allGodis method was called only once
        verify(godisService, times(1)).allGodis();
    }

    @Test
    public void testGetGodisByRating() throws Exception {
        Double rating = 4.5;
        Godis godis = new Godis("Godis1", "Type1", 4.5);

        //create optional instance of Godis object
        Optional<Godis> optionalGodis = Optional.of(godis);

        //mock
        Mockito.when(godisService.byRating(rating)).thenReturn(optionalGodis);

        //pass rating as path variable to api endpoint
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/godis/rating/{rating}", rating))
                .andExpect(MockMvcResultMatchers.status().isOk())
                //return result of request
                .andReturn();

        String response = result.getResponse().getContentAsString();
        //turn response into godis object
        Godis responseGodis = new ObjectMapper().readValue(response, Godis.class);
        //asert that response has same name and type as original godis
        assertEquals(godis.getName(), responseGodis.getName());
        assertEquals(godis.getType(), responseGodis.getType());
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


}
