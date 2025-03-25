package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/*
* File: ueserTest.java
* Author: Rees Hart and Maya Vanderpool
* Purpose: This test file tests the various methods in the user.java class
*  and acheives 90% (or greater) code coverage.  
*/
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

class userTest {
    
    private static final String TEST_DB = "Users.json";
    
    private void setupTest() throws Exception {
        File dbFile = new File(TEST_DB);
        String originalDbContent = null;
        if (dbFile.exists()) {
            originalDbContent = Files.readString(Paths.get(TEST_DB));
        }
        
        Field usersField = User.class.getDeclaredField("users");
        usersField.setAccessible(true);
        Map<String, User> users = (Map<String, User>) usersField.get(null);
        users.clear();
        
        Files.deleteIfExists(Paths.get(TEST_DB));
    }
    
    private void cleanupTest() throws Exception {
        Files.deleteIfExists(Paths.get(TEST_DB));
    }
    
    @Test
    void testConstructor() throws Exception {
        setupTest();
        User user = new User();
        assertNull(user.getUsername());
        assertNull(user.getHashedPwd());
        assertNull(user.getSalt());
        cleanupTest();
    }
    
    @Test
    void testConstructor2() throws Exception {
        setupTest();
        User user = new User("testUser", "password123");
        assertEquals("testUser", user.getUsername());
        assertNotNull(user.getSalt());
        assertNotNull(user.getHashedPwd());
        cleanupTest();
    }
    
    @Test
    void testSettersGetters() throws Exception {
        setupTest();
        User user = new User();
        
        user.setUsername("newUsername");
        assertEquals("newUsername", user.getUsername());
        
        user.setSalt("testSalt");
        assertEquals("testSalt", user.getSalt());
        
        user.setHashedPwd("hashedPassword");
        assertEquals("hashedPassword", user.getHashedPwd());
        cleanupTest();
    }
    
    @Test
    void testMakeSalt() throws Exception {
        setupTest();
        User user = new User();
        String salt1 = user.makeSalt();
        String salt2 = user.makeSalt();
        
        assertNotNull(salt1);
        assertNotNull(salt2);
        assertNotEquals(salt1, salt2);
        cleanupTest();
    }
    
    @Test
    void testHashPwd() throws Exception {
        setupTest();
        User user = new User();
        String salt = user.makeSalt();
        
        String hashedPwd1 = user.hashPwd("password123", salt);
        String hashedPwd2 = user.hashPwd("password123", salt);
        String hashedPwd3 = user.hashPwd("differentPassword", salt);
        
        assertNotNull(hashedPwd1);
        assertEquals(hashedPwd1, hashedPwd2);
        assertNotEquals(hashedPwd1, hashedPwd3);
        cleanupTest();
    }
    
    @Test
    void testIsUserNew() throws Exception {
        setupTest();
        boolean result = User.isUser("newUser", "password123");
        
        assertTrue(result);
        
        File dbFile = new File(TEST_DB);
        assertTrue(dbFile.exists());
        
        ObjectMapper mapper = new ObjectMapper();
        Map<String, User> users = mapper.readValue(dbFile, new TypeReference<Map<String, User>>() {});
        
        assertTrue(users.containsKey("newUser"));
        assertEquals("newUser", users.get("newUser").getUsername());
        cleanupTest();
    }
    
    @Test
    void testIsUserExistingValidPwd() throws Exception {
        setupTest();
        User.isUser("existingUser", "password123");
        
        boolean result = User.isUser("existingUser", "password123");
        
        assertTrue(result);
        cleanupTest();
    }
    
    @Test
    void testIsUserExistingWrongPwd() throws Exception {
        setupTest();
        User.isUser("existingUser", "password123");
        
        boolean result = User.isUser("existingUser", "wrongPassword");
        
        assertFalse(result);
        cleanupTest();
    }
    
    @Test
    void testValidateUserCorrectPwd() throws Exception {
        setupTest();
        User.isUser("validUser", "password123");
        
        boolean result = User.validateUser("validUser", "password123");
        
        assertTrue(result);
        cleanupTest();
    }
    
    @Test
    void testValidateUserWrongPwd() throws Exception {
        setupTest();
        User.isUser("validUser", "password123");
        
        boolean result = User.validateUser("validUser", "wrongPassword");
        
        assertFalse(result);
        cleanupTest();
    }
    
    @Test
    void testValidateNoUser() throws Exception {
        setupTest();
        boolean result = User.validateUser("nonExistentUser", "password");
        
        assertFalse(result);
        cleanupTest();
    }
    
    @Test
    void testLoadUsersEmptyMap() throws Exception {
        setupTest();
        Field usersField = User.class.getDeclaredField("users");
        usersField.setAccessible(true);
        HashMap<String, User> emptyMap = new HashMap<>();
        usersField.set(null, emptyMap);
        
        User testUser = new User("dbUser", "dbPassword");
        Map<String, User> dbUsers = new HashMap<>();
        dbUsers.put("dbUser", testUser);
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(TEST_DB), dbUsers);
        
        User.validateUser("dbUser", "dbPassword");
        
        Map<String, User> users = (Map<String, User>) usersField.get(null);
        assertTrue(users.containsKey("dbUser"));
        cleanupTest();
    }
    
    @Test
    void testLoadUsersFileNotFound() throws Exception {
        setupTest();
        Files.deleteIfExists(Paths.get(TEST_DB));
        
        Field usersField = User.class.getDeclaredField("users");
        usersField.setAccessible(true);
        HashMap<String, User> emptyMap = new HashMap<>();
        usersField.set(null, emptyMap);
        
        User.validateUser("anyUser", "anyPassword");
        
        Map<String, User> users = (Map<String, User>) usersField.get(null);
        assertTrue(users.isEmpty());
        cleanupTest();
    }
    
    @Test
    void testLoadUsersIOException() throws Exception {
        setupTest();
        File dbDir = new File(TEST_DB);
        dbDir.mkdir();
        
        try {
            Field usersField = User.class.getDeclaredField("users");
            usersField.setAccessible(true);
            HashMap<String, User> emptyMap = new HashMap<>();
            usersField.set(null, emptyMap);
            
            User.validateUser("anyUser", "anyPassword");
            
            Map<String, User> users = (Map<String, User>) usersField.get(null);
            assertTrue(users.isEmpty());
        } finally {
            dbDir.delete();
            cleanupTest();
        }
    }
    
    @Test
    void testSaveUserIOException() throws Exception {
        setupTest();
        File dbDir = new File(TEST_DB);
        dbDir.mkdir();
        
        try {
            User.isUser("newUser", "password123");
        } finally {
            dbDir.delete();
            cleanupTest();
        }
    }
}