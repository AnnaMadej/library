package com.aniamadej.Library.Models.Services;
import com.aniamadej.Library.Models.UserModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@NoArgsConstructor
public class UserService {
    UserModel user = new UserModel();
}
