package br.com.techtoy.techtoy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.techtoy.techtoy.model.Categoria;
import br.com.techtoy.techtoy.repository.CategoriaRepository;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    //CRUD

    //Create
    public Categoria adicionar(Categoria categoria){
        categoria.setId(0);
        return categoriaRepository.save(categoria);
    }
    //Read
    public List<Categoria> obterTodos(){
        return categoriaRepository.findAll();
    }
    public Categoria obterPorId(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        if(categoria.isEmpty()){
            throw new RuntimeException("Não existe uma categoria com o ID " + id);
        }
        return categoria.get();
    }
    //Update
    public Categoria atualizar(Long id, Categoria categoria){
        obterPorId(id);
        categoria.setId(id);
        return categoriaRepository.save(categoria);
    }
    //Delete
    public void deletar(Long id){
        obterPorId(id);
        categoriaRepository.deleteById(id);
    }
}

