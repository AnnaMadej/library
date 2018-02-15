package com.aniamadej.Library.Models.Repositories;

import com.aniamadej.Library.Models.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Integer>{
    boolean existsByLogin(String login);
    boolean existsByLoginAndPassword(String login, String password);
    UserModel findByLogin(String login);
}
