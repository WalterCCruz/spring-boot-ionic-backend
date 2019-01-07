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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.net.URI;
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

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageService imageService;


    @Value("${img.prefix.client.profile}")
    private String prefix;


    @Value("${img.profile.size}")
    private Integer size;

    public Cliente find(Integer id) {

        UserSS user = UserService.authenticated();
        if (user == null || user.hasRole(Perfil.ADMIN) && id.equals(user.getId())) {
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

    public Cliente findByEmail(String email) {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
            throw new AuthorizationException("Acesso negado");
        }

        Cliente obj = clienteRepository.findByEmail(email);
        if (obj == null) {
            throw new ObjectNotFoundException(
                    "Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
        }
        return obj;
    }


    public Page<Cliente> findPage(Integer page, Integer linesPePage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPePage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }


    public Cliente fromDTO(ClienteDTO objetoDto) {
        return new Cliente(objetoDto.getId(), objetoDto.getNome(), objetoDto.getEmail(), null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objetoDto) {
        Cliente cli = new Cliente(null, objetoDto.getNome(), objetoDto.getEmail(), objetoDto.getCpfOuCnpj(),
                TipoCliente.toEnum(objetoDto.getTipo()), pe.encode(objetoDto.getSenha()));

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


    public URI uploadProfilePicture(MultipartFile multipartFile) {

        UserSS user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage,size);



        String fileName = prefix + user.getId() + ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }


}
