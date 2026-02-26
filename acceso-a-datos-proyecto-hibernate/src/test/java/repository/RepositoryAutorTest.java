package repository;

import jakarta.persistence.EntityManager;
import org.example.ej3.model.Autor;
import org.example.ej3.repository.RepositoryAutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RepositoryAutorTest {

    @Mock private EntityManager emMock;

    @InjectMocks private RepositoryAutor repository;

    private Autor autor;

    @BeforeEach
    public void setUp(){
        autor = new Autor();
        autor.setId(1L);
    }

    @Test
    public void testFindByID(){
        when(emMock.find(Autor.class,1L)).thenReturn(autor);
        Autor result = repository.findById(emMock,1L);

        verify(emMock).find(Autor.class,1L);
        assertEquals(result,autor);
    }

    @Test
    public void testPersist(){
        repository.persist(emMock,autor);
        verify(emMock).persist(autor);
    }

    @Test
    public void testMerge(){
        when(emMock.merge(autor)).thenReturn(autor);
        Autor result = repository.merge(emMock,autor);

        verify(emMock).merge(autor);
        assertEquals(result, autor);
    }

    @Test
    public void testRemove(){
        repository.remove(emMock,autor);
        verify(emMock).remove(autor);
    }
}
