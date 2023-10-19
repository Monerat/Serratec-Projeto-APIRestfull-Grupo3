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
import br.com.techtoy.techtoy.dto.produto.ProdutoResponseDTO;
import br.com.techtoy.techtoy.model.Categoria;
import br.com.techtoy.techtoy.model.Produto;
import br.com.techtoy.techtoy.model.exceptions.ResourceBadRequest;
import br.com.techtoy.techtoy.model.exceptions.ResourceNotFound;
import br.com.techtoy.techtoy.repository.CategoriaRepository;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ModelMapper mapper;
    //CRUD

    //Create
    @Transactional
    public CategoriaResponseDTO adicionar(CategoriaRequestDTO categoriaRequest){
        
        Categoria categoria = mapper.map(categoriaRequest, Categoria.class);

        categoria.setId(0);
        categoria = categoriaRepository.save(categoria);
        
        return mapper.map(categoria, CategoriaResponseDTO.class);
    }
    //Read publico

    public List<CategoriaResponseDTO> obterTodosPublic(){
        List<Categoria> categoriasModel = categoriaRepository.findAll();
        List<CategoriaResponseDTO> categoriaResponse = new ArrayList<>();

        for(Categoria categoria : categoriasModel){            
            //verifica se a Categoria está ativa.
            if(categoria.isAtivo()){
                categoriaResponse.add(mapper.map(categoria, CategoriaResponseDTO.class));                
            }
        }
        return categoriaResponse;
    }

    public CategoriaResponseDTO obterPorIdPublic(Long id){
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        CategoriaResponseDTO categoriaResponse = new CategoriaResponseDTO();

        if(categoria.isEmpty()){
            throw new ResourceNotFound("Produto não foi encontrado na base com o Id: "+id);
        }
        
        if(categoria.get().isAtivo()){
            categoriaResponse = mapper.map(categoria.get(), CategoriaResponseDTO.class);
        }else{
            throw new ResourceBadRequest("O Produto com id "+id+" está inativo");
        }     
        return categoriaResponse;
    }

    //Read private
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
        
        return mapper.map(categoriaModel, CategoriaResponseDTO.class);
    }
    //Delete
    public void deletar(Long id){
        obterPorId(id);
        categoriaRepository.deleteById(id);
    }
}

