package io.github.avocoders.userservicespring.controller;

import io.github.avocoders.userservicespring.dto.CreateUserRequest;
import io.github.avocoders.userservicespring.dto.UpdateUserRequest;
import io.github.avocoders.userservicespring.dto.UserResponse;
import io.github.avocoders.userservicespring.exception.UserNotFoundException;
import io.github.avocoders.userservicespring.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void getById_shouldReturnUser_whenUserExists() throws Exception {
        LocalDateTime createdAt = LocalDateTime.of(2026, 9, 2, 12, 0);
        UserResponse response = new UserResponse(
                1L,
                "Anna",
                "anna@ya.ru",
                20,
                createdAt
        );

        when(userService.getById(1L)).thenReturn(response);
        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Anna"))
                .andExpect(jsonPath("$.email").value("anna@ya.ru"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.createdAt").value("2026-09-02T12:00:00"));

    }

    @Test
    void getAll_shouldReturnUsers_whenUsersExist() throws Exception {
        LocalDateTime createdAt1 = LocalDateTime.of(2024, 8, 5, 14, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2025, 3, 7, 16, 0);
        LocalDateTime createdAt3 = LocalDateTime.of(2026, 9, 1, 9, 0);

        List<UserResponse> responses = List.of(
                new UserResponse(
                1L,
                "Veronika",
                "veronika@ya.ru",
                32,
                createdAt1 ),
                new UserResponse(
                2L,
                "Aleksey",
                "aleksey@ya.ru",
                36,
                createdAt2 ),
                new UserResponse(
                3L,
                "Kirill",
                "kirill@ya.ru",
                3,
                createdAt3 )
        );

        when(userService.getAll()).thenReturn(responses);
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[0].name").value("Veronika"))
                .andExpect(jsonPath("$[1].name").value("Aleksey"))
                .andExpect(jsonPath("$[2].name").value("Kirill"))
                .andExpect(jsonPath("$[0].email").value("veronika@ya.ru"))
                .andExpect(jsonPath("$[1].email").value("aleksey@ya.ru"))
                .andExpect(jsonPath("$[2].email").value("kirill@ya.ru"))
                .andExpect(jsonPath("$[0].age").value(32))
                .andExpect(jsonPath("$[1].age").value(36))
                .andExpect(jsonPath("$[2].age").value(3))
                .andExpect(jsonPath("$[0].createdAt").value("2024-08-05T14:00:00"))
                .andExpect(jsonPath("$[1].createdAt").value("2025-03-07T16:00:00"))
                .andExpect(jsonPath("$[2].createdAt").value("2026-09-01T09:00:00"))
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void create_shouldCreateUser_whenRequestIsValid() throws Exception {
        LocalDateTime createdAt = LocalDateTime.of(2026, 8, 5, 5, 0);
        UserResponse response = new UserResponse(1L, "Dominica", "dominika@ya.ru", 2, createdAt);
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName("Dominica");
        createUserRequest.setEmail("dominika@ya.ru");
        createUserRequest.setAge(2);

        when(userService.create(any(CreateUserRequest.class))).thenReturn(response);
        mockMvc.perform(post("/api/users")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Dominica"))
                .andExpect(jsonPath("$.email").value("dominika@ya.ru"))
                .andExpect(jsonPath("$.age").value(2))
                .andExpect(jsonPath("$.createdAt").value("2026-08-05T05:00:00"));
    }

    @Test
    void update_shouldUpdateUser_whenRequestIsValid() throws Exception {
        LocalDateTime createdAt = LocalDateTime.of(2026, 2, 20, 13, 0);
        UserResponse response = new UserResponse(3L, "Dominica", "dominika@ya.ru", 2, createdAt);
        UpdateUserRequest userRequest = new UpdateUserRequest();
        userRequest.setName("Dominica");
        userRequest.setEmail("dominika@ya.ru");
        userRequest.setAge(2);

        when(userService.update(eq(3L), any(UpdateUserRequest.class))).thenReturn(response);
        mockMvc.perform(put("/api/users/{id}", 3L)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Dominica"))
                .andExpect(jsonPath("$.email").value("dominika@ya.ru"))
                .andExpect(jsonPath("$.age").value(2))
                .andExpect(jsonPath("$.createdAt").value("2026-02-20T13:00:00"));
    }

    @Test
    void delete_shouldDeleteUser_whenUserExists() throws Exception {
        Long id = 5L;
        mockMvc.perform(delete("/api/users/{id}", id))
                .andExpect(status().isNoContent());
        verify(userService).delete(id);

    }

    @Test
    void getById_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
        Long id = 10L;
        when(userService.getById(id)).thenThrow(new UserNotFoundException(id));
        mockMvc.perform(get("/api/users/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll_shouldReturnEmptyList_whenUsersDoNotExist() throws Exception {
        when(userService.getAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void create_shouldReturnBadRequest_whenRequestIsInvalid() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName("Dominica");
        createUserRequest.setEmail("dominikaya.ru");
        createUserRequest.setAge(20);

        mockMvc.perform(post("/api/users")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest))
                        )
                .andExpect(status().isBadRequest());
        verifyNoInteractions(userService);
    }

    @Test
    void update_shouldReturnBadRequest_whenRequestIsInvalid() throws Exception {
        Long id = 3L;
        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("  ");
        request.setEmail("fd@re.ru");
        request.setAge(4);

        mockMvc.perform(put("/api/users/{id}", id)
                           .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                        )
                .andExpect(status().isBadRequest());
        verifyNoInteractions(userService);
    }

    @Test
    void update_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
        Long id = 3L;
        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("Alya");
        request.setEmail("fbdd@re.ru");
        request.setAge(4);

        when(userService.update(eq(id), any(UpdateUserRequest.class))).thenThrow(new UserNotFoundException(id));
        mockMvc.perform(put("/api/users/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isNotFound());
        verify(userService).update(eq(id), any(UpdateUserRequest.class));
    }

    @Test
    void delete_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
        Long id = 5L;

        doThrow(new UserNotFoundException(id)).when(userService).delete(id);
        mockMvc.perform(delete("/api/users/{id}", id)).andExpect(status().isNotFound());
        verify(userService).delete(id);

    }

}