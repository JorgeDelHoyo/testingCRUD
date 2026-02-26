package repository;

import jakarta.persistence.EntityManager;
import org.example.ej3.model.Editorial;
import org.example.ej3.repository.RepositoryEditorial;
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
public class RepositoryEditorialTest {

    @Mock private EntityManager emMock;

    @InjectMocks private RepositoryEditorial repository;

    private Editorial editorial;

    @BeforeEach
    public void setUp(){
        editorial = new Editorial();
        editorial.setId(1L);
    }

    @Test
    public void testFindByID(){
        when(emMock.find(Editorial.class,1L)).thenReturn(editorial);
        Editorial result = repository.findById(emMock,1L);

        verify(emMock).find(Editorial.class,1L);
        assertEquals(result,editorial);
    }

    @Test
    public void testPersist(){
        repository.persist(emMock,editorial);
        verify(emMock).persist(editorial);
    }

    @Test
    public void testMerge(){
        when(emMock.merge(editorial)).thenReturn(editorial);
        Editorial result = repository.merge(emMock,editorial);

        verify(emMock).merge(editorial);
        assertEquals(result,editorial);
    }

    @Test
    public void testRemove(){
        repository.remove(emMock,editorial);
        verify(emMock).remove(editorial);
    }
}
