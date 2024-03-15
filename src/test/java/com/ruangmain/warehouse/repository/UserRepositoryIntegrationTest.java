package com.ruangmain.warehouse.repository;


import com.ruangmain.warehouse.Util;
import com.ruangmain.warehouse.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void userRepositorySaveReturnSaveUser() {
        User user = Util.user();

        User savedUser = repository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void userRepositoryFindAllReturnMoreThanOneUser() {
        User user = Util.user();
        User admin = Util.admin();

        repository.save(user);
        repository.save(admin);
        List<User> users = repository.findAll();

        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(users.size()).isEqualTo(2);
    }

    @Test
    public void userRepositoryFindByIdReturnUser() {
        User user = Util.user();

        repository.save(user);
        User savedUser = repository.findById(user.getId()).get();

        Assertions.assertThat(savedUser).isNotNull();
    }

    @Test
    public void userRepositoryFindByEmailReturnUser() {
        User user = Util.user();

        repository.save(user);
        User savedUser = repository.findByEmail(user.getEmail()).get();

        Assertions.assertThat(savedUser).isNotNull();
    }

    @Test
    public void userRepositoryDeleteByIdDeleteUser() {
        User user = Util.user();
        User admin = Util.admin();

        repository.save(user);
        repository.save(admin);
        List<User> usersBeforeDelete = repository.findAll();
        repository.deleteById(user.getId());
        List<User> usersAfterDelete = repository.findAll();

        Assertions.assertThat(usersBeforeDelete.size()).isEqualTo(2);
        Assertions.assertThat(usersAfterDelete.size()).isEqualTo(1);
    }

}
