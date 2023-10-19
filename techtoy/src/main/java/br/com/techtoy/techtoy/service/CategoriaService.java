package br.com.techtoy.techtoy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.techtoy.techtoy.dto.categoria.CategoriaRequestDTO;
import br.com.techtoy.techtoy.dto.categoria.CategoriaResponseDTO;
import br.com.techtoy.techtoy.dto.log.LogRequestDTO;
import br.com.techtoy.techtoy.model.Categoria;
import br.com.techtoy.techtoy.model.Enum.EnumLog;
import br.com.techtoy.techtoy.model.Enum.EnumTipoEntidade;
import br.com.techtoy.techtoy.model.exceptions.ResourceNotFound;
import br.com.techtoy.techtoy.repository.CategoriaRepository;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LogService logService;

    @Autowired
    private ModelMapper mapper;
    //CRUD

    //Create
    @Transactional
    public CategoriaResponseDTO adicionar(CategoriaRequestDTO categoriaRequest){
        
        Categoria categoria = mapper.map(categoriaRequest, Categoria.class);

        categoria.setId(0);
        categoria = categoriaRepository.save(categoria);
        
        //Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logService.adicionar(logRequestDTO, EnumLog.CREATE, EnumTipoEntidade.CATEGORIA, "", 
                   logService.mapearObjetoParaString(categoria));

        return mapper.map(categoria, CategoriaResponseDTO.class);
    }
    //Read
    public List<CategoriaResponseDTO> obterTodos(){
        
        List<Categoria> categorias = categoriaRepository.findAll();

        List<CategoriaResponseDTO> categoriasResponse = new ArrayList<>();

        for (Categoria categoria : categorias){
            categoriasResponse.add(mapper.map(categoria, CategoriaResponseDTO.class));
        }
        
        return categoriasResponse;
    }
    public CategoriaResponseDTO obterPorId(Long id) {
        Optional<Categoria> optCategoria = categoriaRepository.findById(id);

        if(optCategoria.isEmpty()){
            throw new ResourceNotFound("Não existe uma categoria com o ID " + id);
        }
        return mapper.map(optCategoria.get(), CategoriaResponseDTO.class);
    }
    //Update
    @Transactional
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO categoriaRequest){
        
        Categoria categoriaBase = mapper.map(obterPorId(id), Categoria.class);
        
        Categoria categoriaModel = mapper.map(categoriaRequest, Categoria.class);

        categoriaModel.setId(id);
        if(categoriaModel.getNome() == null){
            categoriaModel.setNome(categoriaBase.getNome());
        }
        if(categoriaModel.getObservacao() == null){
            categoriaModel.setObservacao(categoriaBase.getNome());
        }
        if(categoriaBase.isAtivo() != categoriaModel.isAtivo()){
            categoriaModel.setAtivo(false);
        }

        categoriaModel = categoriaRepository.save(categoriaModel);
        
        //Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();

        //Verificar se o Produto foi ativado
        if (categoriaBase.isAtivo() != categoriaModel.isAtivo()){
            // Usando a porra do ternario aqui, se ativo for true ele ACTIVOU, cc ele DESACTIVOU :D
            EnumLog logStatus = categoriaModel.isAtivo() ? EnumLog.ACTIVATE : EnumLog.DEACTIVATE;
            logService.adicionar(logRequestDTO, logStatus, EnumTipoEntidade.CATEGORIA, 
                    logService.mapearObjetoParaString(categoriaBase),
                    logService.mapearObjetoParaString(categoriaModel)
                    );
        }

        //Registrar Mudanças UPDATE na Auditoria
        logService.adicionar(logRequestDTO, EnumLog.UPDATE, EnumTipoEntidade.CATEGORIA, 
                    logService.mapearObjetoParaString(categoriaBase),
                    logService.mapearObjetoParaString(categoriaModel)
                    );
        

        return mapper.map(categoriaModel, CategoriaResponseDTO.class);
    }
    //Delete
    public void deletar(Long id){
        obterPorId(id);
        categoriaRepository.deleteById(id);

    //Fazer Auditoria
    LogRequestDTO logRequestDTO = new LogRequestDTO();
    logService.adicionar(logRequestDTO, EnumLog.DELETE, EnumTipoEntidade.CATEGORIA, "", "");
        
    }
}

