package model;

import org.example.ej3.model.Editorial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditorialTest {

    @Test
    public void testMetodosEditorial(){
        Editorial e = new Editorial("a","b");
        e.setNombre("Carlos");
        e.setPais("Londres");

        assertEquals("b",e.getPais());
        assertEquals("Carlos",e.getNombre());
    }
}
