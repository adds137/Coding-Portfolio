package com.scrumoftheearth.springbootapi.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.scrumoftheearth.springbootapi.controller.UserController;
import com.scrumoftheearth.springbootapi.error.NotUniqueException;
import com.scrumoftheearth.springbootapi.model.User;
import com.scrumoftheearth.springbootapi.security.SecurityUserService;
import com.scrumoftheearth.springbootapi.service.UserService;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.SerializationUtils;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * This Calss handles the Intergration tests for the HTTP Layer.
 * Tests and setup is partly based on the following resource: https://reflectoring.io/spring-boot-web-controller-test/
 * Author: Matthew Walters
 */
// TODO: MOST TESTS ARE NOW FLAWED DUE TO CHANGES WITH HANDLING DATES
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserAPITests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityUserService mockSecurityUserService;

    @MockBean
    private UserService mockUserService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper;

    private List<User> testUsers;

    private SimpleDateFormat dateTimeFormatter;

    private Date timeNow;

    @BeforeEach
    void init() {

        dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); /* https://mkyong.com/java8/java-8-how-to-format-localdatetime/ */
        objectMapper = new ObjectMapper();

        testUsers = new ArrayList<>();

        // 0: Valid user
        testUsers.add(new User("OriginalUserNameTest", "FirstName1", "LastName1", "HomeAddress1", "0499999999", "password", "password"));

        // 1: Valid User, but UserName is not unique
        testUsers.add(new User("DuplicateUserNameTest", "FirstName2", "LastName2", "HomeAddress2", "0499999999", "password", "password"));

        // 2: Valid Saved User with ID = 3 and CreatedAt Date
        User testUser3 = new User("TestUser3", "FirstName3", "LastName3", "HomeAddress3", "0499999999", "password", "password");
        LocalDateTime customTime = LocalDateTime.now();
        // Custom time to match json format (due to LocalDateTime having additional information not expressed in json
        timeNow = new Date(customTime.getYear(), customTime.getMonthValue(), customTime.getDayOfMonth(), customTime.getHour(), customTime.getMinute(), customTime.getSecond());
        ReflectionTestUtils.setField(testUser3, "createdAt", timeNow);
        ReflectionTestUtils.setField(testUser3, "id", 3L);
        testUsers.add(testUser3);

        /* Mock UserService class */

        // Invalid Get
        try {
            when(mockUserService.getById(-1L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource " + -1L + " Not Found!"));
        } catch (Throwable throwable) {
            // DO NOTHING
        }

        // User 0: Unique Username and Valid Save
        when(mockUserService.saveUser(eq(testUsers.get(0)))).thenReturn(testUsers.get(0));
        when(mockUserService.checkUserNameNotUnique(testUsers.get(0).getUserName())).thenReturn(false);
        try {
            when(mockUserService.getById(1L)).thenReturn(testUsers.get(0));
        } catch (Throwable throwable) {
            // DO NOTHING
        }

        // User 1: Non-Unique Username and Throw Exception Save
        when(mockUserService.saveUser(eq(testUsers.get(1)))).thenThrow(new NotUniqueException("UserName must be Unique!", HttpStatus.BAD_REQUEST, "userName"));
        when(mockUserService.checkUserNameNotUnique(testUsers.get(1).getUserName())).thenReturn(true);
        try {
            when(mockUserService.getById(2L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource " + 2L + " Not Found!"));
        } catch (Throwable throwable) {
            // DO NOTHING
        }

        // User 2: User for Get
        when(mockUserService.saveUser(eq(testUsers.get(2)))).thenReturn(testUsers.get(2));
        when(mockUserService.checkUserNameNotUnique(testUsers.get(2).getUserName())).thenReturn(false);
        try {
            when(mockUserService.getById(3L)).thenReturn(testUsers.get(2));
        } catch (Throwable throwable) {
            // DO NOTHING
        }
    }

    // TODO: MODIFY TEST METHODS TO USE PRINCIPAL AS SHOWN BELOW
    //  https://stackoverflow.com/questions/45561471/mock-principal-for-spring-rest-controller

    /* Tests for URI POST '/user/' */
    @Nested
    public class CreateTests {

        // TODO: Fix test for not testing Passwords
//        @Test
//        void NotLoggedIn_PostValidUser_HTTP201AndUser() throws Exception {
//            String testUserAsJson = objectMapper.writeValueAsString(testUsers.get(0));
//
//            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
//                    .contentType("application/json")
//                    .content(testUserAsJson)).andExpect(status().isCreated()).andReturn();
//
//            /*  TODO: This should work the other-way in that the object is deserialize into a user object
//                    and all the fields are checked.
//            * */
//            // TODO: FIX ERROR REGARDING INCORRECT MOCK
//            String resultJson = result.getResponse().getContentAsString();
//            assertEquals(testUserAsJson, resultJson);
//        }

        @Test
        void NotLoggedIn_PostValidNonUniqueUser_ThenHTTP400AndJsonError() throws Exception {
            String testUserAsJson = objectMapper.writeValueAsString(testUsers.get(1));

            /* https://mkyong.com/spring-boot/spring-test-how-to-test-a-json-array-in-jsonpath/ */
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                    .contentType("application/json")
                    .content(testUserAsJson))
                    .andExpect(status().isBadRequest()).andReturn();

            String expectedJson = "{\"errors\":{\"userName\":\"User Name must be unique!\"}}";
            String resultJson = result.getResponse().getContentAsString();
            assertEquals(expectedJson, resultJson);
        }

        @ParameterizedTest
        @NullAndEmptySource
        void NotLoggedIn_PostInvalidFields_ThenHTTP400AndJsonErrors(String nullOrBlank) throws Exception {
            User testUser = new User(nullOrBlank, nullOrBlank, nullOrBlank, nullOrBlank, nullOrBlank, nullOrBlank, nullOrBlank);
            String testUserAsJson = objectMapper.writeValueAsString(testUser);

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                    .contentType("application/json")
                    .content(testUserAsJson))
                    .andExpect(status().isBadRequest()).andReturn();

            String expectedJson = "{\"errors\":{\"firstName\":\"First Name cannot be blank!\",\"lastName\":\"Last Name cannot be blank!\",\"phoneNumber\":\"Phone Number cannot be blank!\",\"userName\":\"User Name cannot be blank!\",\"homeAddress\":\"Home Address cannot be blank!\", \"password\":\"Password must be valid!\", \"passwordConfirmation\":\"Password Confirmation must be valid!\"}}";
            String resultJson = result.getResponse().getContentAsString();
            /* https://www.baeldung.com/jsonassert */
            JSONAssert.assertEquals(expectedJson, resultJson, JSONCompareMode.LENIENT);
        }
    }

    /* Tests for URI GET '/user/{id}/' */
    @Nested
    public class ReadTests {

        @Ignore
        void NotLoggedIn_UserExistsWithID3_ThenHTTP302AndUser() throws Exception {

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/3")
            ).andExpect(status().isFound()).andReturn();
            String resultJson = result.getResponse().getContentAsString();
            /* https://stackoverflow.com/questions/30997362/how-to-modify-jsonnode-in-java */
            String userString = objectMapper.writeValueAsString(testUsers.get(2));
            JsonNode jsonUser = objectMapper.readTree(userString);
            ObjectNode jsonNode = jsonUser.deepCopy();
            jsonNode.put("createdAt", dateTimeFormatter.format(testUsers.get(2).getCreatedAt()));
            JSONAssert.assertEquals(jsonNode.toString(), resultJson, JSONCompareMode.LENIENT);
        }

        @Test
        void NotLoggedIn_DoesNotExist_ThenHTTP404() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/user/-1")
            ).andExpect(status().isNotFound()).andExpect(status().reason("Resource -1 Not Found!"));
        }

    }

    /* Tests for URI PUT '/user/{id}/' */
    @Nested
    public class UpdateTests {

        // TODO: Clean up Update Tests

        @Ignore
        void NotLoggedIn_EditedUserName_ThenHTTP200AndUser() throws Exception {
            User editedUser = (User) SerializationUtils.deserialize(SerializationUtils.serialize(testUsers.get(2)));

            assert editedUser != null;
            editedUser.setUserName("NewUniqueName");

            /* NOTE need to set the date format to be of the Jackson @JsonFormat style set in the model.
            /* Otherwise writeValueToString will produce a json tree of all date fields eg. month, year, nano, etc.
            /* when we want a single string. */
            String editedUserAsJson = objectMapper.writeValueAsString(editedUser);
            ObjectNode editedJsonNode = objectMapper.readTree(editedUserAsJson).deepCopy();
            editedJsonNode.put("createdAt", dateTimeFormatter.format(testUsers.get(2).getCreatedAt()));
            editedUserAsJson = editedJsonNode.toString();

            /* https://stackoverflow.com/questions/64036/how-do-you-make-a-deep-copy-of-an-object*/
            User resultUser = (User) SerializationUtils.deserialize(SerializationUtils.serialize(editedUser));

            assert resultUser != null;
            ReflectionTestUtils.setField(resultUser, "updatedAt", timeNow);

            when(mockUserService.checkUserNameNotUnique(editedUser.getUserName())).thenReturn(false);
            when(mockUserService.updateUser(eq(testUsers.get(2)), eq(editedUser))).thenReturn(resultUser);

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/user/3")
                    .contentType("application/json")
                    .content(editedUserAsJson)).andExpect(status().isOk()).andReturn();
            String resultJson = result.getResponse().getContentAsString();

            String expectedUserAsString = objectMapper.writeValueAsString(resultUser);
            ObjectNode expectedUserNode = objectMapper.readTree(expectedUserAsString).deepCopy();
            expectedUserNode.put("updatedAt", dateTimeFormatter.format(resultUser.getUpdatedAt()));
            expectedUserNode.put("createdAt", dateTimeFormatter.format(resultUser.getCreatedAt()));
            expectedUserAsString = expectedUserNode.toString();

            JSONAssert.assertEquals(expectedUserAsString, resultJson, JSONCompareMode.LENIENT);
        }

        @Ignore
        void NotLoggedIn_SameUNameEditedFields_ThenHTTP200AndUser() throws Exception {
            User editedUser = (User) SerializationUtils.deserialize(SerializationUtils.serialize(testUsers.get(2)));

            assert editedUser != null;
            editedUser.setFirstName("NewFirstName");
            editedUser.setLastName("NewLastName");

            /* NOTE need to set the date format to be of the Jackson @JsonFormat style set in the model.
            /* Otherwise writeValueToString will produce a json tree of all date fields eg. month, year, nano, etc.
            /* when we want a single string. */
            String editedUserAsJson = objectMapper.writeValueAsString(editedUser);
            ObjectNode editedJsonNode = objectMapper.readTree(editedUserAsJson).deepCopy();
            editedJsonNode.put("createdAt", dateTimeFormatter.format(testUsers.get(2).getCreatedAt()));
            editedUserAsJson = editedJsonNode.toString();

            /* https://stackoverflow.com/questions/64036/how-do-you-make-a-deep-copy-of-an-object*/
            User resultUser = (User) SerializationUtils.deserialize(SerializationUtils.serialize(editedUser));

            assert resultUser != null;
            ReflectionTestUtils.setField(resultUser, "updatedAt", timeNow);

            when(mockUserService.checkUserNameNotUnique(editedUser.getUserName())).thenReturn(false);
            when(mockUserService.updateUser(eq(testUsers.get(2)), eq(editedUser))).thenReturn(resultUser);

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/user/3")
                    .contentType("application/json")
                    .content(editedUserAsJson)).andExpect(status().isOk()).andReturn();
            String resultJson = result.getResponse().getContentAsString();

            String expectedUserAsString = objectMapper.writeValueAsString(resultUser);
            ObjectNode expectedUserNode = objectMapper.readTree(expectedUserAsString).deepCopy();
            expectedUserNode.put("updatedAt", dateTimeFormatter.format(resultUser.getUpdatedAt()));
            expectedUserNode.put("createdAt", dateTimeFormatter.format(resultUser.getCreatedAt()));
            expectedUserAsString = expectedUserNode.toString();

            JSONAssert.assertEquals(expectedUserAsString, resultJson, JSONCompareMode.LENIENT);
        }

        @Test
        void NotLoggedIn_NewNonUniqueUserName_ThenHTTP400() throws Exception {
            User editedUser = (User) SerializationUtils.deserialize(SerializationUtils.serialize(testUsers.get(2)));

            assert editedUser != null;
            editedUser.setUserName("NonUniqueUserName");

            /* NOTE need to set the date format to be of the Jackson @JsonFormat style set in the model.
            /* Otherwise writeValueToString will produce a json tree of all date fields eg. month, year, nano, etc.
            /* when we want a single string. */
            String editedUserAsJson = objectMapper.writeValueAsString(editedUser);
            ObjectNode editedJsonNode = objectMapper.readTree(editedUserAsJson).deepCopy();
            editedJsonNode.put("createdAt", dateTimeFormatter.format(testUsers.get(2).getCreatedAt()));
            editedUserAsJson = editedJsonNode.toString();

            when(mockUserService.checkUserNameNotUnique(editedUser.getUserName())).thenReturn(true);
            when(mockUserService.updateUser(eq(testUsers.get(2)), eq(editedUser))).thenThrow(new NotUniqueException("SQL Error", HttpStatus.INTERNAL_SERVER_ERROR, "userName"));

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/user/3")
                    .contentType("application/json")
                    .content(editedUserAsJson)).andExpect(status().isBadRequest()).andReturn();

            String expectedJson = "{\"errors\":{\"userName\":\"User Name must be unique!\"}}";
            String resultJson = result.getResponse().getContentAsString();

            JSONAssert.assertEquals(expectedJson, resultJson, JSONCompareMode.LENIENT);
        }

        @ParameterizedTest
        @NullAndEmptySource
        void NotLoggedIn_InvalidFields_ThenHTTP400(String nullOrBlank) throws Exception {
            User editedUser = (User) SerializationUtils.deserialize(SerializationUtils.serialize(testUsers.get(2)));

            assert editedUser != null;
            editedUser.setFirstName(nullOrBlank);
            editedUser.setLastName(nullOrBlank);
            editedUser.setHomeAddress(nullOrBlank);
            editedUser.setPhoneNumber(nullOrBlank);
            editedUser.setUserName(nullOrBlank);
            editedUser.setPassword(nullOrBlank);
            editedUser.setPasswordConfirmation(nullOrBlank);

            /* NOTE need to set the date format to be of the Jackson @JsonFormat style set in the model.
            /* Otherwise writeValueToString will produce a json tree of all date fields eg. month, year, nano, etc.
            /* when we want a single string. */
            String editedUserAsJson = objectMapper.writeValueAsString(editedUser);
            ObjectNode editedJsonNode = objectMapper.readTree(editedUserAsJson).deepCopy();
            editedJsonNode.put("createdAt", dateTimeFormatter.format(testUsers.get(2).getCreatedAt()));
            editedUserAsJson = editedJsonNode.toString();

            when(mockUserService.checkUserNameNotUnique(editedUser.getUserName())).thenReturn(false);
            when(mockUserService.updateUser(eq(testUsers.get(2)), eq(editedUser))).thenThrow(new NotUniqueException("SQL Error", HttpStatus.INTERNAL_SERVER_ERROR, "userName"));

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/user/3")
                    .contentType("application/json")
                    .content(editedUserAsJson)).andExpect(status().isBadRequest()).andReturn();

            String expectedJson = "{\"errors\":{\"firstName\":\"First Name cannot be blank!\",\"lastName\":\"Last Name cannot be blank!\",\"phoneNumber\":\"Phone Number cannot be blank!\",\"userName\":\"User Name cannot be blank!\",\"homeAddress\":\"Home Address cannot be blank!\", \"password\":\"Password must be valid!\", \"passwordConfirmation\":\"Password Confirmation must be valid!\"}}";
            String resultJson = result.getResponse().getContentAsString();

            JSONAssert.assertEquals(expectedJson, resultJson, JSONCompareMode.LENIENT);
        }

        @Test
        void NotLoggedIn_NoneExistentUser_ThenHTTP404() throws Exception {
            User editedUser = (User) SerializationUtils.deserialize(SerializationUtils.serialize(testUsers.get(2)));

            assert editedUser != null;
            editedUser.setUserName("Username");

            //
            String editedUserAsJson = objectMapper.writeValueAsString(editedUser);
            ObjectNode editedJsonNode = objectMapper.readTree(editedUserAsJson).deepCopy();
            editedJsonNode.put("createdAt", dateTimeFormatter.format(testUsers.get(2).getCreatedAt()));
            editedUserAsJson = editedJsonNode.toString();

            when(mockUserService.checkUserNameNotUnique(editedUser.getUserName())).thenReturn(false);
            when(mockUserService.updateUser(eq(testUsers.get(2)), eq(editedUser))).thenReturn(editedUser);

            mockMvc.perform(MockMvcRequestBuilders.put("/api/user/-1")
                    .contentType("application/json")
                    .content(editedUserAsJson)).andExpect(status().isNotFound()).andExpect(status().reason("Resource -1 Not Found!"));
        }
    }

    // TODO: Implement delete tests once api functionality is complete.
//    /* Tests for URI DELETE '/user/{id}/' */
//    @Nested
//    public class DeleteTests {
//
//    }
}
