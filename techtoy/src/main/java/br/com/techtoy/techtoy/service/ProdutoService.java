package br.com.techtoy.techtoy.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.techtoy.techtoy.dto.log.LogRequestDTO;
import br.com.techtoy.techtoy.dto.produto.ProdutoRequestDTO;
import br.com.techtoy.techtoy.dto.produto.ProdutoResponseDTO;
import br.com.techtoy.techtoy.model.Produto;
import br.com.techtoy.techtoy.model.Usuario;
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
    private ModelMapper mapper;
    //CRUD

    //Create
    @Transactional
    public ProdutoResponseDTO adicionar(ProdutoRequestDTO produtoRequest){
        Produto produtoModel = mapper.map(produtoRequest, Produto.class);

        produtoModel.setId(0);
        
        produtoModel = produtoRepository.save(produtoModel);
        produtoModel.setImagem(verificaImagem(produtoModel.getId()));
        produtoRepository.save(produtoModel);

        //Fazer Auditoria
        // LogRequestDTO logRequestDTO = new LogRequestDTO();
        // Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // logService.adicionar(usuarioLogado, logRequestDTO, EnumLog.CREATE, EnumTipoEntidade.PRODUTO, "", 
        //            logService.mapearObjetoParaString(produtoModel));

        return mapper.map(produtoModel, ProdutoResponseDTO.class);
    }

    //imagem
    public String verificaImagem(Long id) {
        String folderPath = "src/main/resources/img/produtos/";
        String fileName = String.valueOf(id);

        File file = new File(folderPath + fileName + ".png");
        
        if (file.exists()) {
            return file.getAbsolutePath();
        } throw new ResourceNotFound("Arquivo não encontrado na base com o nome: "+ fileName);
    
    }

    //Read

    //Publico
    public List<ProdutoResponseDTO> obterTodosPublic(){
        List<Produto> produtoModel = produtoRepository.findAll();
        List<ProdutoResponseDTO> produtoResponse = new ArrayList<>();

        for (Produto produto : produtoModel){
            //verifica se a Categoria está ativa.
            if(produto.getCategoria().getAtivo()){
                //verifica se o Produto está ativo.
                if(produto.getAtivo()){
                    produtoResponse.add(mapper.map(produto, ProdutoResponseDTO.class));
                }
            }            
        }
        //pegar o arquiivo pelo path em imagem.
        //transformar para base 64.
        //setar no atributo image esse valor.
        
        return produtoResponse;
    }

    public ProdutoResponseDTO obterPorIdPublic(Long id){
        Optional<Produto> produto = produtoRepository.findById(id);
        ProdutoResponseDTO produtoResponse = new ProdutoResponseDTO();

        if(produto.isEmpty()){
            throw new ResourceNotFound("Produto não foi encontrado na base com o Id: "+id);
        }
        if(produto.get().getCategoria().getAtivo()){
            if(produto.get().getAtivo()){
                produtoResponse = mapper.map(produto.get(), ProdutoResponseDTO.class);
            }else{
                throw new ResourceBadRequest("O Produto com id "+id+" está inativo");
            }
        }else{
            throw new ResourceBadRequest("A Categoria do Produto com id "+id+" está inativa");
        }

        return produtoResponse;
    }
    
    //Privado
    public List<ProdutoResponseDTO> obterTodos(){
        List<Produto> produtos = produtoRepository.findAll();
    
        return produtos
            .stream()
            .map(produto -> mapper.map(produto, ProdutoResponseDTO.class))
            .collect(Collectors.toList());
    }

    public ProdutoResponseDTO obterPorId(Long id){
        Optional<Produto> produto = produtoRepository.findById(id);

        if(produto.isEmpty()){
            throw new ResourceNotFound("Produto não foi encontrado na base com o Id: "+id);
        }
        return mapper.map(produto.get(), ProdutoResponseDTO.class);
    }

    //Update
    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produtoRequest){
        Produto produtoBase = mapper.map(obterPorId(id), Produto.class);
        Produto produtoModel = mapper.map(produtoRequest, Produto.class);

        produtoModel.setId(id);
        if (produtoModel.getNome()==null){
            produtoModel.setNome(produtoBase.getNome());
        }
        if (produtoModel.getObservacao()==null){
            produtoModel.setObservacao(produtoBase.getObservacao());
        }
        if (produtoModel.getValorUn()==null){
            produtoModel.setValorUn(produtoBase.getValorUn());
        }
        if (produtoModel.getAtivo()==null){
            produtoModel.setAtivo(produtoBase.getAtivo());//entrou aqui nao alterou
        }
        if(produtoModel.getImagem()==null){
            produtoModel.setCategoria(produtoBase.getCategoria());
        }
        if(produtoModel.getCategoria()==null){
            produtoModel.setCategoria(produtoBase.getCategoria());
        }

        produtoModel.setImagem(verificaImagem(produtoModel.getId()));
        produtoModel = produtoRepository.save(produtoModel);
        
        //Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();

        //Verificar se o Produto foi ativado
        if (produtoBase.getAtivo() != produtoModel.getAtivo()){
            // Usando a porra do ternario aqui, se ativo for true ele ACTIVOU, cc ele DESACTIVOU :D
            EnumLog logStatus = produtoModel.getAtivo() ? EnumLog.ACTIVATE : EnumLog.DEACTIVATE;
            Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            logService.adicionar(usuarioLogado, logRequestDTO, logStatus, EnumTipoEntidade.PRODUTO, 
                    logService.mapearObjetoParaString(produtoBase),
                    logService.mapearObjetoParaString(produtoModel)
                    );
        }

        //Registrar Mudanças UPDATE na Auditoria
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logService.adicionar(usuarioLogado, logRequestDTO, EnumLog.UPDATE, EnumTipoEntidade.PRODUTO, 
                    logService.mapearObjetoParaString(produtoBase),
                    logService.mapearObjetoParaString(produtoModel)
                    );
        
        return mapper.map(produtoModel, ProdutoResponseDTO.class);
    }

    //Delete
    public void deletar(Long id){
        obterPorId(id);
        produtoRepository.deleteById(id);

        //Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logService.adicionar(usuarioLogado, logRequestDTO, EnumLog.DELETE, EnumTipoEntidade.PRODUTO, "", "");

    }
}
