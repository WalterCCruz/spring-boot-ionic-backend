package com.waltercruz.cursomc;

import com.waltercruz.cursomc.domain.*;
import com.waltercruz.cursomc.domain.Enums.EstadoPagamento;
import com.waltercruz.cursomc.domain.Enums.TipoCliente;
import com.waltercruz.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;


/* CommandLineRunner Executar a instanciação no momento que a app iniciar*/
@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    @Autowired
    private EstadoRepository estadoRepository;


    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;


    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");
        Categoria cat3 = new Categoria(null, "Cama mesa e banho");
        Categoria cat4 = new Categoria(null, "Eletronicos");
        Categoria cat5 = new Categoria(null, "Jardinagem");
        Categoria cat6 = new Categoria(null, "Decoracao");
        Categoria cat7 = new Categoria(null, "Perfumaria");



        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade c1 = new Cidade(null,"Uberlandia",est1);
        Cidade c2 = new Cidade(null,"São Paulo",est2);
        Cidade c3 = new Cidade(null,"Campinas",est2);


        Produto p1 = new Produto(null,"Computador",20000.00);
        Produto p2 = new Produto(null,"Impressora",800.00);
        Produto p3 = new Produto(null,"Mouse",80.00);


        cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
        cat2.getProdutos().addAll(Arrays.asList(p2));

        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
        p3.getCategorias().addAll(Arrays.asList(cat1));

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2,c3));





        Cliente cli1 =  new Cliente(null,"Maria Silva","mariasilva@gmail.com","88852563697", TipoCliente.PESSOAFISICA);
        cli1.getTelefones().addAll(Arrays.asList("222222222","4564654654"));

//       Endereco e1 = new Endereco(null,"Rua Teste","237","casa","789798798",cli1,c1);
//       Endereco e2 = new Endereco(null,"Rua Teste2","237","casa","789798798",cli1,c1);

       // cli1.getEnderecos().addAll(Arrays.asList(e1,e2));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(null,sdf.parse("12/11/2018 18:00"),cli1,null);

        Pedido ped2 = new Pedido(null,sdf.parse("13/11/2018 10:00"),cli1,null);

        Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO,ped1,6);
        ped1.setPagamento(pgto1);

        Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE,ped2,sdf.parse("31/12/2099 00:00"),null);
        ped2.setPagamento(pgto2);

        ItemPedido ip1 = new ItemPedido(ped1, p1,0.00, 1,2000.00);
        ItemPedido ip2 = new ItemPedido(ped2, p3,0.00, 2,80.00);
        ItemPedido ip3 = new ItemPedido(ped2, p2,100.00, 1,800.00);

        ped1.getItens().addAll(Arrays.asList(ip1,ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));


        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));


        cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));


        estadoRepository.saveAll(Arrays.asList(est1,est2));
        cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
        categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));
        produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
        clienteRepository.saveAll(Arrays.asList(cli1));
        //enderecoRepository.saveAll(Arrays.asList(e1,e2));
        pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
        pagamentoRepository.saveAll(Arrays.asList(pgto1,pgto2));
        itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));




    }
}
