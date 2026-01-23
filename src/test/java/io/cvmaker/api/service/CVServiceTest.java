package io.cvmaker.api.service;

import io.cvmaker.api.model.CV;
import io.cvmaker.api.model.User;
import io.cvmaker.api.repository.CVRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

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
        CV cv = CV.builder().id("cvId").userId("1aaa").cvName("CV 1").build();
        //When
        when(userService.findByGoogleId("1aaa")).thenReturn(user);
        when(cvRepository.findByUserId("1")).thenReturn(Collections.singletonList(cv));
        //Then
        List<CV> cvs = cvService.findByUserId("1aaa");
        assertThat(cvs.getFirst().getCvName()).isEqualTo(cv.getCvName());
    }

    @Test
    public void should_save_cv() {

    }

    @Test
    public void should_update_cv() {

    }

    @Test
    public void should_throw_exception_when_cv_not_found_when_updating() {

    }

    @Test
    public void should_throw_exception_when_user_not_found_when_updating() {

    }

    @Test
    public void should_delete_cv() {

    }

    @Test
    public void should_throw_exception_when_cv_not_found_when_deleting() {

    }

    @Test
    public void should_throw_exception_when_user_not_found_when_deleting() {

    }

}
