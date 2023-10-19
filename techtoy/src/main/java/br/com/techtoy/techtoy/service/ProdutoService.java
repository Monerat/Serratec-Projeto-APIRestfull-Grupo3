package br.com.techtoy.techtoy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ModelMapper mapper;
    //CRUD

    //Create
    @Transactional
    public ProdutoResponseDTO adicionar(ProdutoRequestDTO produtoRequest){
        Produto produtoModel = mapper.map(produtoRequest, Produto.class);

        produtoModel.setId(0);
        produtoRepository.save(produtoModel);

        //Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logService.adicionar(logRequestDTO, EnumLog.CREATE, EnumTipoEntidade.PRODUTO, "", 
                   logService.mapearObjetoParaString(produtoModel));

        return mapper.map(produtoModel, ProdutoResponseDTO.class);
    }

    //Read

    //Publico
    public List<ProdutoResponseDTO> obterTodosPublic(){
        List<Produto> produtoModel = produtoRepository.findAll();
        List<ProdutoResponseDTO> produtoResponse = new ArrayList<>();

        for (Produto produto : produtoModel){
            //verifica se a Categoria está ativa.
            if(produto.getCategoria().isAtivo()){
                //verifica se o Produto está ativo.
                if(produto.isAtivo()){
                    produtoResponse.add(mapper.map(produto, ProdutoResponseDTO.class));
                }
            }            
        }
        return produtoResponse;
    }

    public ProdutoResponseDTO obterPorIdPublic(Long id){
        Optional<Produto> produto = produtoRepository.findById(id);
        ProdutoResponseDTO produtoResponse = new ProdutoResponseDTO();

        if(produto.isEmpty()){
            throw new ResourceNotFound("Produto não foi encontrado na base com o Id: "+id);
        }
        if(produto.get().getCategoria().isAtivo()){
            if(produto.get().isAtivo()){
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
        if (produtoModel.isAtivo()==null){
            produtoModel.setAtivo(produtoBase.isAtivo());//entrou aqui nao alterou
        }
        if(produtoModel.getCategoria()==null){
            produtoModel.setCategoria(produtoBase.getCategoria());
        }

        produtoModel = produtoRepository.save(produtoModel);
        
        //Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();

        //Verificar se o Produto foi ativado
        if (produtoBase.isAtivo() != produtoModel.isAtivo()){
            // Usando a porra do ternario aqui, se ativo for true ele ACTIVOU, cc ele DESACTIVOU :D
            EnumLog logStatus = produtoModel.isAtivo() ? EnumLog.ACTIVATE : EnumLog.DEACTIVATE;
            logService.adicionar(logRequestDTO, logStatus, EnumTipoEntidade.PRODUTO, 
                    logService.mapearObjetoParaString(produtoBase),
                    logService.mapearObjetoParaString(produtoModel)
                    );
        }

        //Registrar Mudanças UPDATE na Auditoria
        logService.adicionar(logRequestDTO, EnumLog.UPDATE, EnumTipoEntidade.PRODUTO, 
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
        logService.adicionar(logRequestDTO, EnumLog.DELETE, EnumTipoEntidade.PRODUTO, "", "");

    }
}
