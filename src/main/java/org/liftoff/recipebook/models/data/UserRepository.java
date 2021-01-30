package org.liftoff.recipebook.models.data;

import org.liftoff.recipebook.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}
