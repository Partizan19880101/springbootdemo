package com.epam.training.springbootdemo;


import com.epam.training.springbootdemo.model.User;
import com.epam.training.springbootdemo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DaoTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    @Before
    public void cleanTestData() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "delete from usr where email not like ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%@mail.com");
            ps.executeUpdate();
        }
    }

    @Test
    public void testFindAllUsers() {
        List<User> users = userService.findAll(1, 20);
        assertNotNull(users);
        assertEquals(6, users.size());
        for (User user : users) {
            assertNotNull(user.getId());
            assertNotNull(user.getName());
            assertNotNull(user.getEmail());
        }
    }

    @Test
    public void testSaveUpdateDeleteUser() throws Exception{
        User testUsr = new User();
        testUsr.setName("TestUsr");
        testUsr.setEmail("TestUsr@mail.com");
        testUsr.setPassword("123");
        testUsr.setUsername("Test");
        testUsr.setActive(true);

        userService.save(testUsr);
        assertNotNull(testUsr.getId());

        User findUser = userService.findById(testUsr.getId());
        assertEquals(testUsr.getName(), findUser.getName());
        assertEquals(testUsr.getEmail(), findUser.getEmail());

        testUsr.setEmail("charlize@latte.net");
        userService.update(testUsr);

        findUser = userService.findById(testUsr.getId());
        assertEquals(testUsr.getEmail(), findUser.getEmail());

        userService.deleteById(testUsr.getId());

        User userDelete = userService.findById(testUsr.getId());
        assertNull(userDelete);
    }
}