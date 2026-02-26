package model;

import org.example.ej3.model.Editorial;
import org.example.ej3.model.Libro;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibroTest {

    @Test
    public void testMetodosLibro(){
        Libro l = new Libro();
        Editorial ed = new Editorial();
        l.setTitulo("Titulo");
        l.setIsbn("aa");
        l.setPrecio(2.2);
        l.setEditorial(ed);

        assertEquals(ed,l.getEditorial());
        assertEquals(2.2,l.getPrecio());
        assertEquals("aa",l.getIsbn());
        assertEquals("Titulo",l.getTitulo());
    }
}
