package io.cvmaker.api.service;

import io.cvmaker.api.exception.RessourceNotFoundException;
import io.cvmaker.api.exception.UnauthorizedAccessException;
import io.cvmaker.api.model.CV;
import io.cvmaker.api.model.User;
import io.cvmaker.api.repository.CVRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CVServiceTest {
    @Mock
    private CVRepository cvRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private CVService cvService;

    @Test
    public void should_return_cvs_by_user_id() {
        //Given
        User user = User.builder().id("1").googleId("1aaa").email("test@example.com").build();
        List<CV> cvs = List.of(CV.builder().id("cvId").userId("1aaa").cvName("CV 1").build(),
                CV.builder().id("cvId").userId("1aaa").cvName("CV 2").build());
        //When
        when(userService.findByGoogleId("1aaa")).thenReturn(user);
        when(cvRepository.findByUserId("1")).thenReturn(cvs);
        //Then
        List<CV> cvsReturn = cvService.findByUserId("1aaa");
        assertThat(cvsReturn.getFirst().getCvName()).isEqualTo(cvs.getFirst().getCvName());
    }

    @Test
    public void should_save_cv() {
        User user = User.builder().id("1").googleId("1aaa").email("test@example.com").username("kevin").build();
        CV cv = CV.builder().id("cvId").userId("1aaa").cvName("CV 1").build();

        when(cvRepository.save(cv)).thenReturn(cv);

        CV newCV = cvService.saveCV(cv, "1aaa");
        assertThat(newCV.getCvName()).isEqualTo(cv.getCvName());
    }

    //@Test
    //public void should_update_cv() {

    //}

    @Test
    public void should_throw_exception_when_cv_not_found_when_updating() {
        // GIVEN
        String id = "invalid-id";
        String googleId = "googleId-123";
        when(cvRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(RessourceNotFoundException.class, () -> cvService.updateCV(googleId, id, new CV()));
    }

    @Test
    public void should_throw_exception_when_user_not_found_when_updating() {
        // GIVEN
        String id = "valid-id";
        String googleId = "invalid-googleId-123";
        CV cv = CV.builder().id("valid-id").userId("1aaa").cvName("CV 1").build();
        User user = User.builder().id("1").googleId("valid-googleId-123").email("test@example.com").username("kevin").build();

        // WHEN
        when(cvRepository.findById(id)).thenReturn(Optional.of(cv));
        when(userService.findByGoogleId(googleId)).thenReturn(user);

        // WHEN & THEN
        assertThrows(UnauthorizedAccessException.class, () -> cvService.updateCV(googleId, id, new CV()));
    }

    @Test
    public void should_delete_cv() {
        // GIVEN
        String id = "valid-cv-id";
        String googleId = "valid-googleId-123";
        CV cv = CV.builder().id("valid-cv-id").userId("1aaa").cvName("CV 1").build();
        User user = User.builder().id("1aaa").googleId("valid-googleId-123").email("test@example.com").username("kevin").build();

        // WHEN
        when(cvRepository.findById(id)).thenReturn(Optional.of(cv));
        when(userService.findByGoogleId(googleId)).thenReturn(user);

        // THEN
        cvService.deleteCV(googleId, id);
        verify(cvRepository, times(1)).deleteById(id);
    }

    @Test
    public void should_throw_exception_when_cv_not_found_when_deleting() {
        // GIVEN
        String id = "invalid-id";
        String googleId = "googleId-123";
        when(cvRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(RessourceNotFoundException.class, () -> cvService.deleteCV(googleId, id));
    }

    @Test
    public void should_throw_exception_when_user_not_found_when_deleting() {
        // GIVEN
        String id = "valid-id";
        String googleId = "invalid-googleId-123";
        CV cv = CV.builder().id("valid-id").userId("1aaa").cvName("CV 1").build();
        User user = User.builder().id("1").googleId("valid-googleId-123").email("test@example.com").username("kevin").build();

        // WHEN
        when(cvRepository.findById(id)).thenReturn(Optional.of(cv));
        when(userService.findByGoogleId(googleId)).thenReturn(user);

        // WHEN & THEN
        assertThrows(UnauthorizedAccessException.class, () -> cvService.deleteCV(googleId, id));
    }

}
