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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@NoArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public boolean correctPassword(String password){
        if (!correctPassword(SecurityContextHolder.getContext().getAuthentication().getName(), password)) return false;
        return true;
    }

    public boolean correctPassword(String login, String password){
        if (!bCryptPasswordEncoder.matches(password, userRepository.getPassword(login))) {
            return false;
        }
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
        return modelMapper.map(userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()), UserDto.class);
    }

    public UserDto getFullUserData(String login){
        return modelMapper.map(userRepository.findByLogin(login), UserDto.class);
    }

    public boolean updateProfile(ProfileForm profileForm) {
        if (!correctPassword(profileForm.getOldPassword())) {
            return false;
        }
        UserModel userModel= userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userModel != null){
            userModel.setName(profileForm.getName());
            userModel.setSurname(profileForm.getSurname());
            userRepository.save(userModel);
        }
        return true;
    }

    public Optional<String> changePasswd(PasswordChangeForm passwordChangeForm) {
        if(!correctPassword(passwordChangeForm.getOldPassword())) return Optional.of("badPassword");
        if (!passwordChangeForm.getPassword().equals(passwordChangeForm.getPassword2())) return Optional.of("passwordsError");

        UserModel userModel = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        if (userModel!=null){
            userModel.setPassword(bCryptPasswordEncoder.encode(passwordChangeForm.getPassword()));
            userRepository.save(userModel);
        }
        return Optional.of("changed");
    }

    public ProfileForm getProfileForm(){
        return modelMapper.map(getFullUserData(), ProfileForm.class);
    }

    public ProfileForm getProfileForm(String login){
        return modelMapper.map(getFullUserData(login), ProfileForm.class);
    }
}


