package com.aniamadej.Library.Models.Repositories;

import com.aniamadej.Library.Models.Entities.UserModel;
import com.aniamadej.Library.Models.dtos.LoggedUserDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Integer>{
    boolean existsByLogin(String login);
    UserModel findByLogin(String login);

    @Query("select u.password from UserModel u where login= ?1")
    String getPassword(String login);

    @Query("select new com.aniamadej.Library.Models.dtos.LoggedUserDto(u.login, u.id) from UserModel u where u.login=:login")
    LoggedUserDto getLoggedUserDto(@Param("login")String login);
}
