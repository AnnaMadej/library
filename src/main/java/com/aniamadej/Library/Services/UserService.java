package com.aniamadej.Library.Services;
import com.aniamadej.Library.Models.Forms.PasswordChangeForm;
import com.aniamadej.Library.Models.Forms.ProfileForm;
import com.aniamadej.Library.Models.Forms.RegisterForm;
import com.aniamadej.Library.Models.Repositories.UserRepository;
import com.aniamadej.Library.Models.Entities.UserModel;
import com.aniamadej.Library.Models.dtos.LoggedUserDto;
import com.aniamadej.Library.Models.dtos.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@NoArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    LoggedUserDto user = new LoggedUserDto();

    public boolean checkPassword(String password){
        if (!userRepository.existsByLoginAndPassword(this.getUser().getLogin(), password)) return false;
        return true;
    }

    public boolean loginUser(String login, String password){
        if (!userRepository.existsByLoginAndPassword(login, password)) return false;
            this.setUser(modelMapper.map(userRepository.findByLogin(login), LoggedUserDto.class));
            return true;
    }


    public Optional<String> registerUser(RegisterForm registerForm) {
        if (!registerForm.getPassword().equals(registerForm.getPassword2()))
            return Optional.of("passwordsError");
        if (userRepository.existsByLogin(registerForm.getLogin()))
            return Optional.of("userExists");

        UserModel userModel = modelMapper.map(registerForm, UserModel.class);
        userRepository.save(userModel);
        return Optional.empty();
    }

    public UserDto getFullUserData(){
        return modelMapper.map(userRepository.findByLogin(this.getUser().getLogin()), UserDto.class);
    }

    public boolean updateProfile(ProfileForm profileForm) {
        if(!checkPassword(profileForm.getOldPassword())) return false;
        UserModel userModel = userRepository.findOne(this.getUser().getId());
        modelMapper.map(profileForm, userModel);
        userRepository.save(userModel);
        return true;
    }

    public Optional<String> changePasswd(PasswordChangeForm passwordChangeForm) {
        if(!checkPassword(passwordChangeForm.getOldPassword())) return Optional.of("badPassword");
        if (!passwordChangeForm.getPassword().equals(passwordChangeForm.getPassword2())) return Optional.of("passwordsError");
        UserModel userModel = userRepository.findOne(this.getUser().getId());
        modelMapper.map(passwordChangeForm, userModel);
        userRepository.save(userModel);
        return Optional.of("changed");
    }

    public ProfileForm getProfileForm(){
        return modelMapper.map(getFullUserData(), ProfileForm.class);
    }
}

