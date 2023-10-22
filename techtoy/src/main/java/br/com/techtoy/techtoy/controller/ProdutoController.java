package br.com.techtoy.techtoy.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.techtoy.techtoy.dto.produto.ProdutoRequestDTO;
import br.com.techtoy.techtoy.dto.produto.ProdutoResponseDTO;
import br.com.techtoy.techtoy.service.ProdutoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    /**
     * Cria um novo produto.
     *
     * @param produto O objeto ProdutoRequestDTO contendo os detalhes do produto.
     * @return Um ResponseEntity contendo o ProdutoResponseDTO recém-criado.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProdutoResponseDTO> adicionar(@RequestBody ProdutoRequestDTO produto) {
        return ResponseEntity
                .status(201)
                .body(produtoService.adicionar(produto));
    }

    /**
     * Salva um arquivo de imagem para um produto.
     *
     * @param image O arquivo de imagem a ser salvo.
     * @return Um ResponseEntity com uma mensagem indicando o resultado da operação.
     */
    @PostMapping("/imagem")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> salvarArquivo(@RequestParam("imagem") MultipartFile image) {
        String pathArquivos = "src/main/resources/img/produtos/";
        var caminho = pathArquivos + image.getOriginalFilename();

        try {
            Files.copy(image.getInputStream(), Path.of(caminho), StandardCopyOption.REPLACE_EXISTING);
            return new ResponseEntity<>("{ \"mensagem\": \"Arquivo carregado com sucesso!\"}", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("{ \"mensagem\": \"Erro ao carregar o arquivo!\"}", HttpStatus.OK);
        }
    }

    /**
     * Obtém todos os produtos públicos.
     *
     * @return Um ResponseEntity contendo uma lista de ProdutoResponseDTO.
     */
    @GetMapping("/public")
    public ResponseEntity<List<ProdutoResponseDTO>> obterTodosPublic() {
        return ResponseEntity
                .status(200)
                .body(produtoService.obterTodosPublic());
    }

    /**
     * Obtém um produto público por ID.
     *
     * @param id O ID do produto.
     * @return Um ResponseEntity contendo o ProdutoResponseDTO correspondente.
     */
    @GetMapping("/public/{id}")
    public ResponseEntity<ProdutoResponseDTO> obterPorIdPublic(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(produtoService.obterPorIdPublic(id));
    }

    /**
     * Obtém todos os produtos (requer autorização ADMIN).
     *
     * @return Um ResponseEntity contendo uma lista de ProdutoResponseDTO.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ProdutoResponseDTO>> obterTodos() {
        return ResponseEntity
                .status(200)
                .body(produtoService.obterTodos());
    }

    /**
     * Obtém um produto por ID (requer autorização ADMIN).
     *
     * @param id O ID do produto.
     * @return Um ResponseEntity contendo o ProdutoResponseDTO correspondente.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProdutoResponseDTO> obterPorId(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(produtoService.obterPorId(id));
    }

    /**
     * Atualiza um produto por ID (requer autorização ADMIN).
     *
     * @param id      O ID do produto a ser atualizado.
     * @param produto O objeto ProdutoRequestDTO contendo os novos detalhes do
     *                produto.
     * @return Um ResponseEntity contendo o ProdutoResponseDTO atualizado.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @RequestBody ProdutoRequestDTO produto) {
        return ResponseEntity
                .status(200)
                .body(produtoService.atualizar(id, produto));
    }

    /**
     * Deleta um produto por ID (requer autorização ADMIN).
     *
     * @param id O ID do produto a ser excluído.
     * @return Um ResponseEntity indicando o sucesso da operação.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity
                .status(204)
                .build();
    }
}
