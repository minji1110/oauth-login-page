package minji.oauthlogin.controller;

import lombok.RequiredArgsConstructor;
import minji.oauthlogin.config.PrincipalDetails;
import minji.oauthlogin.entity.User;
import minji.oauthlogin.entity.UserJoinDto;
import minji.oauthlogin.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal PrincipalDetails principalDetails){
        if(principalDetails!=null)
        {
            System.out.println(principalDetails.getUser().getUserId());
        }
        return "home";
    }

    @GetMapping("/welcome")
    public String welcome(Model model,@AuthenticationPrincipal PrincipalDetails principalDetails){
        model.addAttribute("user",principalDetails.getUser());
        return "welcome";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @PostMapping("/join")
    public String join(UserJoinDto userJoinDto){
        User user=User.builder()
                .userId(userJoinDto.getUserId())
                .userEmail(userJoinDto.getUserEmail())
                .userName(userJoinDto.getUserName())
                .userPassword(passwordEncoder.encode(userJoinDto.getUserPassword()))
                .userRole(userJoinDto.getUserRole())
                .build();

        userRepository.save(user);
        return "redirect:loginForm";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin만 접속 가능";
    }

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication){
        System.out.println("authentication.getPrincipal() = " + authentication.getPrincipal());
        return "login 정보 확인";
    }
}
