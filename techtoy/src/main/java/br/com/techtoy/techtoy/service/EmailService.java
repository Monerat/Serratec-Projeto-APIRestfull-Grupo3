package br.com.techtoy.techtoy.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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
        
        Double subtotal = 0.0;
        String produtos = "";
        Integer produtoQuantidade = 0;

        

        for (PedidoItem pedidoItem : pedido.getPedidoItens()) {
            subtotal += pedidoItem.getSubTotal();
            pedidoItem.setProduto(produtoRepository.findById(pedidoItem.getProduto().getId()).get());
            produtos += pedidoItem.getProduto().getNome() + " ,";
            produtoQuantidade += pedidoItem.getQuantidade();
        }
        
        String mensagem = "Olá!<br>" +
                "Cadastro de pedido realizado com sucesso para o usuario: " + nome + "<br><br>" +
                "Número do pedido: " + pedido.getId() + "<br>" +
                "Data do pedido: " + pedido.getDataPedido() + "<br><br>" +

                "Produto: " + produtos + "<br>" +
                "Quantidade: " + produtoQuantidade + "<br>" +
                "Subtotal: " + subtotal + "<br>" +

                "Valor total do pedido: R$" + pedido.getValorTotal() + "<br><br>" +
                "Tenha um excelente dia!<br><br> Sincerely <br>O grupo 3 da disciplina de API Restful do Serratec";

        Email email = new Email("Cadastro de Pedido", mensagem, destinatarios);

        enviar(email);
    }
}