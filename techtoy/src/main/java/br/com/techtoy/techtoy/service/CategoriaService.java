package br.com.techtoy.techtoy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.techtoy.techtoy.dto.categoria.CategoriaRequestDTO;
import br.com.techtoy.techtoy.dto.categoria.CategoriaResponseDTO;
import br.com.techtoy.techtoy.model.Categoria;
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
    public CategoriaResponseDTO adicionar(CategoriaRequestDTO categoriaRequest){
        
        Categoria categoria = mapper.map(categoriaRequest, Categoria.class);

        categoria = categoriaRepository.save(categoria);
        
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
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO categoriaRequest){
        
        obterPorId(id);
        
        categoriaRequest.setId(id);
        Categoria categoria = categoriaRepository.save(mapper.map(categoriaRequest, Categoria.class));

        return mapper.map(categoria, CategoriaResponseDTO.class);
    }
    //Delete
    public void deletar(Long id){
        obterPorId(id);
        categoriaRepository.deleteById(id);
    }
}

