package br.com.techtoy.techtoy.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.techtoy.techtoy.common.ChecaValores;
import br.com.techtoy.techtoy.dto.categoria.CategoriaResponseDTO;
import br.com.techtoy.techtoy.dto.log.LogRequestDTO;
import br.com.techtoy.techtoy.dto.produto.ProdutoRequestDTO;
import br.com.techtoy.techtoy.dto.produto.ProdutoResponseDTO;
import br.com.techtoy.techtoy.model.Produto;
import br.com.techtoy.techtoy.model.Enum.EnumLog;
import br.com.techtoy.techtoy.model.Enum.EnumTipoEntidade;
import br.com.techtoy.techtoy.model.exceptions.ResourceBadRequest;
import br.com.techtoy.techtoy.model.exceptions.ResourceNotFound;
import br.com.techtoy.techtoy.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LogService logService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ModelMapper mapper;
    // CRUD

    // Create
    @Transactional
    public ProdutoResponseDTO adicionar(ProdutoRequestDTO produtoRequest) {
        Produto produtoModel = mapper.map(produtoRequest, Produto.class);

        

        if (produtoModel.getNome() == null) {
            throw new ResourceBadRequest("Você não inseriu o nome do produto, que é um campo que não pode ser nulo");
        }

        if (produtoModel.getEstoque() == null) {
            throw new ResourceBadRequest("Você não inseriu o estoque do produto, que é um campo que não pode ser nulo");
        }

        if (produtoModel.getValorUn() == null) {
            throw new ResourceBadRequest(
                    "Você não inseriu o valor unitário do produto, que é um campo que não pode ser nulo");
        }

        if (produtoModel.getAtivo() == null) {
            throw new ResourceBadRequest(
                    "Você não inseriu se o produto está ativo ou não, que é um campo que não pode ser nulo");
        }
        // desativar produto caso cat esteja desativada
        if (!categoriaService.obterPorId(produtoModel.getCategoria().getId()).isAtivo()) {
            produtoModel.setAtivo(false);
        }

        ChecaValores.verificaValorDouble(produtoModel.getValorUn());
        ChecaValores.verificaValorInt(produtoModel.getEstoque());
        produtoModel.setId(0);

        produtoModel = produtoRepository.save(produtoModel);
        produtoModel.setImagem(adicionarImagem(produtoModel.getId()));
        produtoRepository.save(produtoModel);

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logService.adicionar(logService.verificarUsuarioLogado(), logRequestDTO, EnumLog.CREATE,
                EnumTipoEntidade.PRODUTO, "",
                logService.mapearObjetoParaString(produtoModel));

        return mapper.map(produtoModel, ProdutoResponseDTO.class);
    }

    // Read

    // Publico
    public List<ProdutoResponseDTO> obterTodosPublic() {
        List<Produto> produtoModel = produtoRepository.findAll();
        List<ProdutoResponseDTO> produtoResponse = new ArrayList<>();

        for (Produto produto : produtoModel) {
            // verifica se a Categoria está ativa.
            if (produto.getCategoria().getAtivo()) {
                // verifica se o Produto está ativo.
                if (produto.getAtivo()) {
                    ProdutoResponseDTO produtoResponseDTO = mapper.map(produto, ProdutoResponseDTO.class);
                    produtoResponseDTO = transformarImgToBase(produtoResponseDTO);
                    produtoResponse.add(produtoResponseDTO);
                }
            }
        }
        return produtoResponse;
    }

    public ProdutoResponseDTO obterPorIdPublic(Long id) {
        ChecaValores.verificaValorLong(id);
        Optional<Produto> produto = produtoRepository.findById(id);
        ProdutoResponseDTO produtoResponse = new ProdutoResponseDTO();

        if (produto.isEmpty()) {
            throw new ResourceNotFound("Produto não foi encontrado na base com o Id: " + id);
        }
        if (produto.get().getCategoria().getAtivo()) {
            if (produto.get().getAtivo()) {
                produtoResponse = mapper.map(produto.get(), ProdutoResponseDTO.class);
                produtoResponse = transformarImgToBase(produtoResponse);
            } else {
                throw new ResourceBadRequest("O Produto com id " + id + " está inativo");
            }

        } else {
            throw new ResourceBadRequest("A Categoria do Produto com id " + id + " está inativa");
        }

        return produtoResponse;
    }

    // Privado
    public List<ProdutoResponseDTO> obterTodos() {
        List<Produto> produtos = produtoRepository.findAll();

        return produtos
                .stream()
                .map(produto -> mapper.map(produto, ProdutoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO obterPorId(Long id) {
        ChecaValores.verificaValorLong(id);
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isEmpty()) {
            throw new ResourceNotFound("Produto não foi encontrado na base com o Id: " + id);
        }

        return mapper.map(produto.get(), ProdutoResponseDTO.class);
    }

    // Update
    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produtoRequest) {
        ChecaValores.verificaValorLong(id);

        Produto produtoBase = mapper.map(obterPorId(id), Produto.class);
        Produto produtoModel = mapper.map(produtoRequest, Produto.class);

        produtoModel.setId(id);
        if (produtoModel.getNome() == null) {
            produtoModel.setNome(produtoBase.getNome());
        }

        if (produtoModel.getObservacao() == null) {
            produtoModel.setObservacao(produtoBase.getObservacao());
        }

        if (produtoModel.getValorUn() == null) {
            produtoModel.setValorUn(produtoBase.getValorUn());
        }

        if (produtoModel.getAtivo() == null) {
            produtoModel.setAtivo(produtoBase.getAtivo());// entrou aqui nao alterou
        }

        if (produtoModel.getImagem() == null) {
            produtoModel.setImagem(produtoBase.getImagem());
        }

        if (produtoModel.getCategoria() == null) {
            produtoModel.setCategoria(produtoBase.getCategoria());
        }

        if (!categoriaService.obterPorId(produtoModel.getCategoria().getId()).isAtivo()) {
            produtoModel.setAtivo(false);
        }

        ChecaValores.verificaValorDouble(produtoModel.getValorUn());
        ChecaValores.verificaValorInt(produtoModel.getEstoque());

        produtoModel.setImagem(verificaImagem(produtoModel.getId()));
        produtoModel = produtoRepository.save(produtoModel);

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();

        // Verificar se o Produto foi ativado
        if (produtoBase.getAtivo() != produtoModel.getAtivo()) {
            EnumLog logStatus = produtoModel.getAtivo() ? EnumLog.ACTIVATE : EnumLog.DEACTIVATE;
            logService.adicionar(logService.verificarUsuarioLogado(), logRequestDTO, logStatus,
                    EnumTipoEntidade.PRODUTO,
                    logService.mapearObjetoParaString(produtoBase),
                    logService.mapearObjetoParaString(produtoModel));
        }

        // Registrar Mudanças UPDATE na Auditoria
        logService.adicionar(logService.verificarUsuarioLogado(), logRequestDTO, EnumLog.UPDATE,
                EnumTipoEntidade.PRODUTO,
                logService.mapearObjetoParaString(produtoBase),
                logService.mapearObjetoParaString(produtoModel));

        return mapper.map(produtoModel, ProdutoResponseDTO.class);
    }

    // Delete
    public void deletar(Long id) {
        ChecaValores.verificaValorLong(id);
        obterPorId(id);
        produtoRepository.deleteById(id);

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logService.adicionar(logService.verificarUsuarioLogado(), logRequestDTO, EnumLog.DELETE,
                EnumTipoEntidade.PRODUTO, "", "");

    }

    // Adicionar imagem ao novo produto
    public String adicionarImagem(Long id) {
        ChecaValores.verificaValorLong(id);
        String folderPath = "src/main/resources/img/produtos/";
        String fileName = String.valueOf(id);

        File file = new File(folderPath + fileName + ".png");

        if (file.exists()) {
            return file.getAbsolutePath();
        }

        throw new ResourceNotFound("Imagem não encontrada na base com o nome: " + fileName);
    }

    // Verificar a existência de Imagem de um produto
    public String verificaImagem(Long id) {
        ChecaValores.verificaValorLong(id);

        String fileName = String.valueOf(id);

        try {
            File file = new File(obterPorId(id).getImagem());

            if (file.exists()) {
                return file.getAbsolutePath();
            }
        } catch (Exception e) {
            throw new ResourceNotFound("Imagem não encontrada na base com o nome: " + fileName);
        }

        throw new ResourceNotFound("Imagem não encontrada na base com o nome: " + fileName);
    }

    // Transformar a imagem em base64
    public ProdutoResponseDTO transformarImgToBase(ProdutoResponseDTO produto) {
        String imagePath = produto.getImagem();

        try {
            byte[] fileContent = Files.readAllBytes(Paths.get(imagePath));
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            produto.setImagem(encodedString);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return produto;
    }

}