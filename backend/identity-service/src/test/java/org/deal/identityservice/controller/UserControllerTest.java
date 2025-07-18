package org.deal.identityservice.controller;

import org.deal.core.dto.ProductCategoryDTO;
import org.deal.core.dto.UserDTO;
import org.deal.core.exception.DealError;
import org.deal.core.request.user.AssignProductCategoryRequest;
import org.deal.core.response.user.UserProfileResponse;
import org.deal.core.util.Mapper;
import org.deal.identityservice.service.UserService;
import org.deal.identityservice.util.BaseUnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.deal.core.util.Constants.ReturnMessages.failedToSave;
import static org.deal.core.util.Constants.ReturnMessages.notFound;
import static org.deal.identityservice.util.TestUtils.ResponseUtils.assertThatResponseFailed;
import static org.deal.identityservice.util.TestUtils.ResponseUtils.assertThatResponseIsSuccessful;
import static org.deal.identityservice.util.TestUtils.UserUtils.createUserRequest;
import static org.deal.identityservice.util.TestUtils.UserUtils.randomUser;
import static org.deal.identityservice.util.TestUtils.UserUtils.updateUserRequest;
import static org.deal.identityservice.util.TestUtils.convertAll;
import static org.deal.identityservice.util.TestUtils.randomString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest extends BaseUnitTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController victim;

    @Test
    void testGetUsers_shouldReturnSuccess() {
        var expectedUsers = List.of(randomUser(), randomUser());
        when(userService.findAll()).thenReturn(Optional.of(convertAll(expectedUsers, UserDTO.class)));

        var response = victim.getUsers();

        verify(userService).findAll();
        assertThatResponseIsSuccessful(response, convertAll(expectedUsers, UserDTO.class));
    }

    @Test
    void testGetUsers_noUsersFound_returnsFailure() {
        when(userService.findAll()).thenReturn(Optional.empty());

        var response = victim.getUsers();

        verify(userService).findAll();
        assertThatResponseFailed(response, List.of(new DealError(notFound(UserDTO.class))), HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetUserById_userFound_returnsSuccess() {
        var expectedUser = randomUser();
        var expectedData = Mapper.mapTo(expectedUser, UserDTO.class);
        when(userService.findById(expectedUser.getId())).thenReturn(Optional.of(expectedData));

        var response = victim.getUserById(expectedUser.getId());

        verify(userService).findById(expectedUser.getId());
        assertThatResponseIsSuccessful(response, expectedData);
    }

    @Test
    void testGetUserById_userNotFound_returnsFailure() {
        var id = UUID.randomUUID();
        when(userService.findById(id)).thenReturn(Optional.empty());

        var response = victim.getUserById(id);

        verify(userService).findById(id);
        assertThatResponseFailed(response, List.of(new DealError(notFound(UserDTO.class, "id", id))), HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetUserProfileById_userFound_returnsSuccess() {
        var user = randomUser();
        var expectedProfile = UserProfileResponse.builder()
                .withEmail(user.getEmail())
                .withUsername(user.getUsername())
                .withRole(user.getRole())
                .withCreatedAt(user.getCreatedAt())
                .withProductCategories(Set.of(new ProductCategoryDTO(user.getProductCategoryIds().stream().toList().getFirst(), randomString())))
                .build();

        when(userService.findProfileById(user.getId())).thenReturn(Optional.of(expectedProfile));

        var response = victim.getUserProfileById(user.getId());

        verify(userService).findProfileById(user.getId());
        assertThatResponseIsSuccessful(response, expectedProfile);
    }

    @Test
    void testGetUserProfileById_userNotFound_returnsFailure() {
        var id = UUID.randomUUID();

        when(userService.findProfileById(id)).thenReturn(Optional.empty());

        var response = victim.getUserProfileById(id);

        verify(userService).findProfileById(id);
        assertThatResponseFailed(
                response,
                List.of(new DealError(notFound(UserProfileResponse.class, "id", id))),
                HttpStatus.NOT_FOUND
        );
    }


    @Test
    void testCreate_userIsCreated_shouldReturnSuccess() {
        var createdUser = randomUser();
        var request = createUserRequest(createdUser);
        var expectedData = Mapper.mapTo(createdUser, UserDTO.class);
        when(userService.create(request)).thenReturn(Optional.of(expectedData));

        var response = victim.create(request);

        verify(userService).create(request);
        assertThatResponseIsSuccessful(response, expectedData);
    }

    @Test
    void testCreate_userIsNotCreated_returnsFailure() {
        when(userService.create(any())).thenReturn(Optional.empty());

        var response = victim.create(createUserRequest(randomUser()));

        verify(userService).create(any());
        assertThatResponseFailed(response, List.of(new DealError(failedToSave(UserDTO.class))), HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUpdate_userIsUpdated_shouldReturnSuccess() {
        var updatedUser = randomUser();
        var request = updateUserRequest(updatedUser);
        var expectedData = Mapper.mapTo(updatedUser, UserDTO.class);
        when(userService.update(request, updatedUser.getFullName(), updatedUser.getAddress(), updatedUser.getCity(), updatedUser.getCountry(), updatedUser.getPostalCode(), updatedUser.getPhoneNumber(), updatedUser.getProfileUrl(), updatedUser.getStoreAddress())).thenReturn(Optional.of(expectedData));

        var response = victim.update(request, updatedUser.getFullName(), updatedUser.getAddress(), updatedUser.getCity(), updatedUser.getCountry(), updatedUser.getPostalCode(), updatedUser.getPhoneNumber(), updatedUser.getProfileUrl(), updatedUser.getStoreAddress());

        verify(userService).update(request, updatedUser.getFullName(), updatedUser.getAddress(), updatedUser.getCity(), updatedUser.getCountry(), updatedUser.getPostalCode(), updatedUser.getPhoneNumber(), updatedUser.getProfileUrl(), updatedUser.getStoreAddress());
        assertThatResponseIsSuccessful(response, expectedData);
    }

    @Test
    void testUpdate_userNotUpdated_returnsFailure() {
        var request = updateUserRequest(randomUser());
        when(userService.update(request, null, null, null, null, null, null, null, null)).thenReturn(Optional.empty());

        var response = victim.update(request, null, null, null, null, null, null, null, null);

        verify(userService).update(request, null, null, null, null, null, null, null, null);
        assertThatResponseFailed(response, List.of(new DealError(notFound(UserDTO.class, "id", request.id()))), HttpStatus.NOT_FOUND);
    }

    @Test
    void testUpdateCategories_userUpdated_returnsSuccess() {
        // Arrange
        var user = randomUser();
        var expectedDto = Mapper.mapTo(user, UserDTO.class);
        var request = new AssignProductCategoryRequest(user.getId(), user.getProductCategoryIds());

        when(userService.assignProductCategories(request)).thenReturn(Optional.of(expectedDto));

        // Act
        var response = victim.updateUserCategories(request);

        // Assert
        verify(userService).assignProductCategories(request);
        assertThatResponseIsSuccessful(response, expectedDto);
    }

    @Test
    void testUpdateCategories_userNotFound_returnsFailure() {
        // Arrange
        var request = new AssignProductCategoryRequest(UUID.randomUUID(), Set.of(UUID.randomUUID()));

        when(userService.assignProductCategories(request)).thenReturn(Optional.empty());

        // Act
        var response = victim.updateUserCategories(request);

        // Assert
        verify(userService).assignProductCategories(request);
        assertThatResponseFailed(
                response,
                List.of(new DealError(notFound(UserDTO.class, "id", request.userId()))),
                HttpStatus.NOT_FOUND
        );
    }



    @Test
    void testDeleteUserById_userDeleted_shouldReturnSuccess() {
        var user = randomUser();
        var expectedData = Mapper.mapTo(user, UserDTO.class);
        when(userService.deleteById(user.getId())).thenReturn(Optional.of(expectedData));

        var response = victim.deleteUserById(user.getId());

        verify(userService).deleteById(user.getId());
        assertThatResponseIsSuccessful(response, expectedData);
    }

    @Test
    void testDeleteUserById_userNotFound_returnsFailure() {
        var id = UUID.randomUUID();
        when(userService.deleteById(id)).thenReturn(Optional.empty());

        var response = victim.deleteUserById(id);

        verify(userService).deleteById(id);
        assertThatResponseFailed(response, List.of(new DealError(notFound(UserDTO.class, "id", id))), HttpStatus.NOT_FOUND);
    }
}