package com.YagoRueda.WorkoutBuddy.service.userServiceTest;

import com.YagoRueda.WorkoutBuddy.Service.UserService;
import com.YagoRueda.WorkoutBuddy.entity.UserEntity;
import com.YagoRueda.WorkoutBuddy.exception.InpuDataException;
import com.YagoRueda.WorkoutBuddy.repository.FollowRepository;
import com.YagoRueda.WorkoutBuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class followTest {

    @Autowired
    private UserService service;

    @Autowired
    private FollowRepository follow_repository;

    @Autowired
    private UserRepository user_repository;


    @Test
    public void correctFollow() {
        UserEntity a = new UserEntity();
        a.setUsername("a");
        a.setEmail("a");
        a.setPassword("a");
        user_repository.save(a);
        UserEntity b = new UserEntity();
        b.setUsername("b");
        b.setEmail("b");
        b.setPassword("b");
        user_repository.save(b);


        service.follow(a.getUsername(), b.getUsername());

        assertTrue(follow_repository.existsByFollowerAndFollowed(a, b));

        if (user_repository.existsByUsername(a.getUsername())) {
            user_repository.delete(a);
        }
        if (user_repository.existsByUsername(b.getUsername())) {
            user_repository.delete(b);
        }

    }

    @Test
    public void existingFollow() {

        UserEntity a = new UserEntity();
        a.setUsername("a");
        a.setEmail("a");
        a.setPassword("a");
        user_repository.save(a);
        UserEntity b = new UserEntity();
        b.setUsername("b");
        b.setEmail("b");
        b.setPassword("b");
        user_repository.save(b);


        service.follow(a.getUsername(), b.getUsername());

        try {
            service.follow(a.getUsername(), b.getUsername());
        } catch (Exception e) {
            assertEquals("los usuarios ya se estan siguiendo", e.getMessage());
        }

        if (user_repository.existsByUsername(a.getUsername())) {
            user_repository.delete(a);
        }
        if (user_repository.existsByUsername(b.getUsername())) {
            user_repository.delete(b);
        }
    }

    @Test
    public void oneUserFollow() {
        UserEntity a = new UserEntity();
        a.setUsername("a");
        a.setEmail("a");
        a.setPassword("a");
        user_repository.save(a);

        try {
            service.follow(a.getUsername(), a.getUsername());
        } catch (Exception e) {
            assertEquals("Un usuario no se puede seguir a si mismo", e.getMessage());
        }

        if (user_repository.existsByUsername(a.getUsername())) {
            user_repository.delete(a);
        }

    }

    @Test
    public void nonExistingFollower() {
        UserEntity a = new UserEntity();
        a.setUsername("a");
        a.setEmail("a");
        a.setPassword("a");

        UserEntity b = new UserEntity();
        b.setUsername("b");
        b.setEmail("b");
        b.setPassword("b");
        user_repository.save(b);

        try {
            service.follow(a.getUsername(), b.getUsername());
        } catch (Exception e) {
            assertEquals("No existe el seguidor", e.getMessage());
        }


        if (user_repository.existsByUsername(a.getUsername())) {
            user_repository.delete(a);
        }
        if (user_repository.existsByUsername(b.getUsername())) {
            user_repository.delete(b);
        }
    }

    @Test
    public void nonExistingFollowed() {
        UserEntity a = new UserEntity();
        a.setUsername("a");
        a.setEmail("a");
        a.setPassword("a");
        user_repository.save(a);
        UserEntity b = new UserEntity();
        b.setUsername("b");
        b.setEmail("b");
        b.setPassword("b");


        try {
            service.follow(a.getUsername(), b.getUsername());
        } catch (Exception e) {
            assertEquals("No existe el seguido", e.getMessage());
        }


        if (user_repository.existsByUsername(a.getUsername())) {
            user_repository.delete(a);
        }
        if (user_repository.existsByUsername(b.getUsername())) {
            user_repository.delete(b);
        }
    }

    @Test
    public void correctUnfollow() {
        UserEntity a = new UserEntity();
        a.setUsername("a");
        a.setEmail("a");
        a.setPassword("a");
        UserEntity b = new UserEntity();
        b.setUsername("b");
        b.setEmail("b");
        b.setPassword("b");
        user_repository.save(a);
        user_repository.save(b);

        service.follow(a.getUsername(), b.getUsername());
        assertTrue(follow_repository.existsByFollowerAndFollowed(a, b));

        service.unfollow(a.getUsername(), b.getUsername());
        assertFalse(follow_repository.existsByFollowerAndFollowed(a, b));

        if (user_repository.existsByUsername(a.getUsername())) {
            user_repository.delete(a);
        }
        if (user_repository.existsByUsername(b.getUsername())) {
            user_repository.delete(b);
        }
    }

    @Test
    public void unfollowWhenNotFollowing() {
        UserEntity a = new UserEntity();
        a.setUsername("a");
        a.setEmail("a");
        a.setPassword("a");
        UserEntity b = new UserEntity();
        b.setUsername("b");
        b.setEmail("b");
        b.setPassword("b");
        user_repository.save(a);
        user_repository.save(b);

        try {
            service.unfollow(a.getUsername(), b.getUsername());
        } catch (InpuDataException e) {
            assertEquals("no existe la relacion entre usuarios", e.getMessage());
        }
        if (user_repository.existsByUsername(a.getUsername())) {
            user_repository.delete(a);
        }
        if (user_repository.existsByUsername(b.getUsername())) {
            user_repository.delete(b);
        }
    }

    @Test
    public void nonExistingUnFollower() {
        UserEntity a = new UserEntity();
        a.setUsername("a");
        a.setEmail("a");
        a.setPassword("a");
        UserEntity b = new UserEntity();
        b.setUsername("b");
        b.setEmail("b");
        b.setPassword("b");
        user_repository.save(a);
        user_repository.save(b);

        service.follow(a.getUsername(), b.getUsername());
        assertTrue(follow_repository.existsByFollowerAndFollowed(a, b));

        user_repository.delete(a);

        try {
            service.unfollow(a.getUsername(), b.getUsername());
        } catch (InpuDataException e) {
            assertEquals("No existe el seguidor", e.getMessage());
        }


        if (user_repository.existsByUsername(a.getUsername())) {
            user_repository.delete(a);
        }
        if (user_repository.existsByUsername(b.getUsername())) {
            user_repository.delete(b);
        }
    }

    @Test
    public void nonExistingunFollowed() {
        UserEntity a = new UserEntity();
        a.setUsername("a");
        a.setEmail("a");
        a.setPassword("a");
        UserEntity b = new UserEntity();
        b.setUsername("b");
        b.setEmail("b");
        b.setPassword("b");
        user_repository.save(a);
        user_repository.save(b);

        service.follow(a.getUsername(), b.getUsername());
        assertTrue(follow_repository.existsByFollowerAndFollowed(a, b));

        user_repository.delete(b);

        try {
            service.unfollow(a.getUsername(), b.getUsername());
        } catch (InpuDataException e) {
            assertEquals("No existe el seguido", e.getMessage());
        }


        if (user_repository.existsByUsername(a.getUsername())) {
            user_repository.delete(a);
        }
        if (user_repository.existsByUsername(b.getUsername())) {
            user_repository.delete(b);
        }
    }
}
