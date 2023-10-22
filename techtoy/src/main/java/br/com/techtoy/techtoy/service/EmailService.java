package br.com.techtoy.techtoy.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.techtoy.techtoy.common.dateConverter;
import br.com.techtoy.techtoy.model.Pedido;
import br.com.techtoy.techtoy.model.PedidoItem;
import br.com.techtoy.techtoy.model.email.Email;
import br.com.techtoy.techtoy.repository.ProdutoRepository;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ProdutoRepository produtoRepository;

    public void enviar(Email email) {

        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom(email.getRemetente());
            helper.setSubject(email.getAssunto());
            helper.setText(email.getMensagem(), true); // true para html
            helper.setTo(email.getDestinatarios()
                    .toArray(new String[email.getDestinatarios().size()]));

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            System.out.println("Problemas ao enviar e-mail de confirmção de ação:");
            System.out.println(e.getMessage());
        }

    }

    public void dispararEmail(String tipoEmail, String destinatario, String nome) {

        List<String> destinatarios = new ArrayList<>();
        destinatarios.add(destinatario);

        String mensagem = "Olá, " + nome + "!<br>" + tipoEmail +
                " realizado com sucesso utilizando o email: " + destinatario + "<br><br>" +
                "Tenha um excelente dia!<br><br> Sincerely <br>O grupo 3 da disciplina de API Restful do Serratec";

        Email email = new Email(tipoEmail, mensagem, destinatarios);

        enviar(email);
    }

    public void dispararEmailPedido(String destinatario, String nome, Pedido pedido) {

        List<String> destinatarios = new ArrayList<>();
        destinatarios.add(destinatario);

        Integer produtoQuantidade = 0;
        String mensagemProduto = "";

        for (PedidoItem pedidoItem : pedido.getPedidoItens()) {

            pedidoItem.setProduto(produtoRepository.findById(pedidoItem.getProduto().getId()).get());
            produtoQuantidade += pedidoItem.getQuantidade();

            mensagemProduto += "<br><li><span style=\"font-size:16px\"><strong>Produtos: "
                    + pedidoItem.getProduto().getNome() + ".</strong></span></li>" +
                    "    <li><span style=\"font-size:16px\"><strong>Quantidade: " + pedidoItem.getQuantidade()
                    + ".</strong></span></li>" +
                    "    <li><span style=\"font-size:16px\"><strong>SubTotal: " + pedidoItem.getSubTotal()
                    + ".</strong></span></li>";
        }

        String mensagem = "<h1><span style=\"text-align:center; margin: 20vw\" ><span style=\"font-size:36px\"><strong>Cadastro de Pedido</strong></span></span></h1>"
                +
                "<p style=\"text-align:center\"><span style=\"font-size:16px\"><strong>" + nome
                + " , seu pedido foi cadastrado com sucesso.</strong></span></p>"
                +
                "<ul>" +
                "    <li><span style=\"font-size:16px\"><strong>Numero do pedido: " + pedido.getId()
                + ".</strong></span></li>" +
                "    <li><span style=\"font-size:16px\"><strong>Data do pedido: "
                + dateConverter.converter(pedido.getDataPedido()) + ".</strong></span></li>" +
                "</ul>" +
                "<ul>" +
                "    <li><span style=\"font-size:16px\"><strong>" + mensagemProduto + "</strong></span></li>" +
                "    <br><li><span style=\"font-size:16px\"><strong>Quantidade de itens: " + produtoQuantidade
                + ".</strong></span></li>" +
                "</ul>" +
                "<ul>" +
                "<br><li><span style=\"font-size:16px\"><strong>Valor total do pedido: R$: " + pedido.getValorTotal()
                + ".</strong></span></li>"
                +
                "</ul>" +
                "<p style=\"text-align:center\"><strong><img alt=\"caixa\" src=\"https://cdn-icons-png.flaticon.com/512/4829/4829979.png\" style=\"height:250px; width:250px\" /></strong></p>"
                +
                "<p style=\"text-align:center\">&nbsp;</p>" +
                "<p style=\"text-align:center\"><span style=\"font-size:16px\"><strong>Tenha um excelente dia!</strong></span></p>"
                +
                "<p style=\"text-align:center\"><span style=\"font-size:16px\"><strong>Sincerely</strong></span></p>" +
                "<p style=\"text-align:center\"><span style=\"font-size:16px\"><strong>O Grupo 3 da disciplina de API Restful do Serratec</strong></span></p>"
                +
                "<p>&nbsp;</p>";

        Email email = new Email("Cadastro de Pedido", mensagem, destinatarios);

        enviar(email);
    }
}