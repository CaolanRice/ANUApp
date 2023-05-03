
package dev.caolan.test;

import dev.caolan.godis.Godis;
import dev.caolan.godis.GodisController;
import dev.caolan.godis.GodisService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GodisControllerTest {

    private GodisService godisService = mock(GodisService.class);
    private GodisController godisController = new GodisController(godisService);

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


}
