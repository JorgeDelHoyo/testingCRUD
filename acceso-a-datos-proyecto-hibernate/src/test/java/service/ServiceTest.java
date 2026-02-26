package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.ej3.JPAUtil;
import org.example.ej3.model.Autor;
import org.example.ej3.model.Editorial;
import org.example.ej3.model.Libro;
import org.example.ej3.service.Service;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock private EntityManager emMock;
    @Mock private EntityTransaction txMock;

    @InjectMocks private Service service;
    private MockedStatic<JPAUtil> jpaUtilMock;

    @BeforeEach
    void setUp() {
        // Configuramos las transacciones para que no den NullPointerException
        lenient().when(emMock.getTransaction()).thenReturn(txMock);

        // Mockeamos la conexión a la base de datos
        jpaUtilMock = mockStatic(JPAUtil.class);
        jpaUtilMock.when(JPAUtil::getEntityManager).thenReturn(emMock);
    }

    @AfterEach
    void tearDown() {
        // Cerramos el mock estático
        if (jpaUtilMock != null) {
            jpaUtilMock.close();
        }
    }


    @Test
    void testCrearDatosIniciales() {
        Editorial editorialMock = new Editorial("Editorial 1","España");
        editorialMock.setLibros(new ArrayList<>());

        when(emMock.find(eq(Editorial.class),any())).thenReturn(editorialMock);

        Long idEditorial = service.crearDatosIniciales();

        verify(txMock,atLeast(1)).begin();
        verify(emMock,atLeast(1)).persist(any(Editorial.class));
        verify(txMock,atLeast(1)).commit();

        assertEquals(3, editorialMock.getLibros().size());
    }

    @Test
    public void testAddLibro(){
        Long idEditorial = 1L;
        Libro libroNuevo = new Libro("Libro1","123",11.1,null);

        Editorial editorialMock = new Editorial("Editorial", "Pais");
        editorialMock.setLibros(new ArrayList<>());

        when(emMock.find(eq(Editorial.class),any())).thenReturn(editorialMock);

        service.addLibro(1L,libroNuevo);

        verify(emMock).find(Editorial.class,idEditorial);
        assertTrue(editorialMock.getLibros().contains(libroNuevo));
    }

    @Test
    public void testListaLibrosEditorial(){
        Long idEditorial = 1L;
        Editorial editorialMock = new Editorial("nombre","Pais");
        List<Libro> listaFalsa = List.of(new Libro(), new Libro());
        editorialMock.setLibros(listaFalsa);

        when(emMock.find(eq(Editorial.class),any())).thenReturn(editorialMock);

        service.listaLibrosEditorial(idEditorial);

        verify(emMock).close();
    }

    @Test
    public void testBuscarLibro(){
        Long idLibro = 1L;
        Libro libroMock = new Libro("123","a",22.3,null);
        when(emMock.find(eq(Libro.class),any())).thenReturn(libroMock);

        Libro resultado = service.buscarLibro(idLibro);

        assertNotNull(resultado);
        verify(emMock).find(eq(Libro.class),any());
    }

    @Test
    public void testAddAutorALibro(){
        Long autorID = 1L;
        Long libroID = 2L;

        Autor autorMock = new Autor();
        Libro libroMock = new Libro();
        libroMock.setAutores(new ArrayList<>());

        when(emMock.find(eq(Libro.class),any())).thenReturn(libroMock);
        when(emMock.find(eq(Autor.class),any())).thenReturn(autorMock);

        service.addAutorALibro(autorID,libroID);

        verify(txMock,atLeast(1)).begin();
        verify(emMock).merge(libroMock);
        verify(txMock,atLeast(1)).commit();
        verify(emMock,atLeast(1)).close();
    }

    @Test
    public void testAddLibroConAutores(){
        Long editoialID = 1L;
        Libro libroMock = new Libro();
        libroMock.setAutores(new ArrayList<>());

        List<Long> autoresIds = List.of(1L,2L);
        Editorial editorialMock = new Editorial();

        when(emMock.find(eq(Editorial.class),any())).thenReturn(editorialMock);

        when(emMock.find(eq(Libro.class),any())).thenReturn(libroMock);
        when(emMock.find(eq(Autor.class),any())).thenReturn(new Autor());

        service.addLibroConAutores(editoialID,libroMock,autoresIds);

        verify(txMock,atLeast(1)).begin();
        verify(emMock,atLeast(1)).persist(libroMock);
        verify(txMock,atLeast(1)).commit();
        verify(emMock,atLeast(1)).close();
    }

    @Test
    public void testListarAutoresLibro(){
        Long libroID = 1L;
        Libro libroMock = new Libro();

        when(emMock.find(eq(Libro.class),any())).thenReturn(libroMock);

        service.listarAutoresLibro(libroID);

        verify(emMock).close();
    }

    @Test
    public void testListarLibrosAutor(){
        Long autorID = 1L;
        Autor autorMock = new Autor();
        when(emMock.find(eq(Autor.class),any())).thenReturn(autorMock);

        service.listarLibrosAutor(autorID);

        verify(emMock).close();
    }

}
