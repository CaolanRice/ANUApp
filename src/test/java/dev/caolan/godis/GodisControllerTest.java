package dev.caolan.godis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@RunWith(MockitoJUnitRunner.class)
public class GodisControllerTest {

    @InjectMocks
    private GodisController godisController;

    @Mock
    private GodisService godisService;

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
        Optional<Godis> optionalGodis = Optional.of(godis);

        Mockito.when(godisService.byType(type)).thenReturn(optionalGodis);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/godis/type/{type}", type))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Godis responseGodis = new ObjectMapper().readValue(response, Godis.class);
        Assert.assertEquals(godis.getName(), responseGodis.getName());
        Assert.assertEquals(godis.getRating(), responseGodis.getRating(), 0.001); //delta = margin of error. 0.001 for precision when comparing floating point
    }

}

