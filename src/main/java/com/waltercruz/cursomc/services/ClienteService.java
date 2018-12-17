package com.waltercruz.cursomc.services;


import com.waltercruz.cursomc.DTO.ClienteDTO;
import com.waltercruz.cursomc.DTO.ClienteNewDTO;
import com.waltercruz.cursomc.domain.Cidade;
import com.waltercruz.cursomc.domain.Cliente;
import com.waltercruz.cursomc.domain.Endereco;
import com.waltercruz.cursomc.domain.Enums.Perfil;
import com.waltercruz.cursomc.domain.Enums.TipoCliente;
import com.waltercruz.cursomc.domain.Security.UserSS;
import com.waltercruz.cursomc.repositories.ClienteRepository;
import com.waltercruz.cursomc.repositories.EnderecoRepository;
import com.waltercruz.cursomc.services.exception.AuthorizationException;
import com.waltercruz.cursomc.services.exception.DataIntegrityException;
import com.waltercruz.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;


    @Autowired
    private BCryptPasswordEncoder pe;


    public Cliente find(Integer id) {

        UserSS user = UserService.authenticated();
        if(user == null || user.hasRole(Perfil.ADMIN) && id.equals(user.getId())){
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }


    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);/*para garantir que o objeto que estou inserindo é novo*/
        obj = clienteRepository.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;

    }

    /*Cliente valendo null insere se já estiver preenchido id é realizada a edicao*/
    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return clienteRepository.save(newObj);

    }


    public void delete(Integer id) {
        find(id);
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel deletar pois há pedidos relacionados.");
        }

    }


    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPePage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPePage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }


    public Cliente fromDTO(ClienteDTO objetoDto) {
        return new Cliente(objetoDto.getId(), objetoDto.getNome(), objetoDto.getEmail(), null, null,null);
    }

    public Cliente fromDTO(ClienteNewDTO objetoDto) {
        Cliente cli = new Cliente(null, objetoDto.getNome(), objetoDto.getEmail(), objetoDto.getCpfOuCnpj(),
        TipoCliente.toEnum(objetoDto.getTipo()),pe.encode(objetoDto.getSenha()));

        Cidade cid = new Cidade(objetoDto.getCidadeId(), null, null);

        Endereco end = new Endereco(null, objetoDto.getLogradouro(), objetoDto.getNumero(), objetoDto.getComplemento(),
        objetoDto.getBairro(), objetoDto.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objetoDto.getTelefone1());
        if (objetoDto.getTelefone2() != null) {
            cli.getTelefones().add(objetoDto.getTelefone2());
        }
        if (objetoDto.getTelefone3() != null) {
            cli.getTelefones().add(objetoDto.getTelefone3());
        }
        return cli;


    }


    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }


}
