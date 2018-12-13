package com.waltercruz.cursomc.services;

import com.waltercruz.cursomc.domain.Cliente;
import com.waltercruz.cursomc.domain.Security.UserSS;
import com.waltercruz.cursomc.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl  implements UserDetailsService {



    @Autowired
    private ClienteRepository clienteRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

     Cliente cli = clienteRepository.findByEmail(email);
     if(cli ==null){
         throw new UsernameNotFoundException(email);
     }
        return new UserSS(cli.getId(),cli.getEmail(),cli.getSenha(),cli.getPerfis());
    }
}
