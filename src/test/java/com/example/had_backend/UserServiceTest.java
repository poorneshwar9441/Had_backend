package com.example.had_backend;

import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.repository.UserInfoRepository;
import com.example.had_backend.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @InjectMocks
    private UserInfoService userInfoService;

    @Test
    public void testGetUserById() {
        // Creating a mock user
        UserInfo mockUser = new UserInfo();
        mockUser.setId(1L);
        mockUser.setName("testUser");

        // Mocking the repository method to return the mock user
        when(userInfoRepository.findByName("testUser")).thenReturn(java.util.Optional.of(mockUser));

        // Calling the service method
        UserInfo retrievedUser = userInfoService.getUserByUsername("testUser");

        // Verify that the correct user was returned
        assertEquals("testUser", retrievedUser.getName());
    }
}

