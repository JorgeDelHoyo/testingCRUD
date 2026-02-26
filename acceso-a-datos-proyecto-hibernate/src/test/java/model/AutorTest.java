package model;

import org.example.ej3.model.Autor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AutorTest {

    @Test
    public void testMetodosAutor(){
        Autor a = new Autor();
        a.setId(1L);
        a.setNombre("Carlos");
        a.setNacionalidad("Español");
        a.setLibros(new ArrayList<>());

        assertEquals("Español", a.getNacionalidad());
        assertEquals("Carlos",a.getNombre());
        assertEquals(1L,a.getId());
    }
}
