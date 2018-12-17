package com.waltercruz.cursomc.services;

import com.waltercruz.cursomc.domain.Security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static UserSS authenticated(){
        try {
            /*Retorna o usuário logado no sistema*/
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            return null;
        }
    }

}
