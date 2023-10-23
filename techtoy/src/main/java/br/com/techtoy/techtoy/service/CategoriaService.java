package br.com.techtoy.techtoy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.techtoy.techtoy.common.ChecaValores;
import br.com.techtoy.techtoy.dto.categoria.CategoriaRequestDTO;
import br.com.techtoy.techtoy.dto.categoria.CategoriaResponseDTO;
import br.com.techtoy.techtoy.dto.log.LogRequestDTO;
import br.com.techtoy.techtoy.model.Categoria;
import br.com.techtoy.techtoy.model.exceptions.ResourceBadRequest;
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
    // CRUD

    // Create
    @Transactional
    public CategoriaResponseDTO adicionar(CategoriaRequestDTO categoriaRequest) {

        Categoria categoriaModel = mapper.map(categoriaRequest, Categoria.class);

        if (categoriaRepository.findByNome(categoriaModel.getNome()).isPresent()) {
            throw new ResourceBadRequest("Nome da categoria deve ser único.");
        }
        if (categoriaModel.getNome() == null) {
            throw new ResourceBadRequest("Você não inseriu o nome da categoria, que é um campo que não pode ser nulo");
        }

        categoriaModel.setId(0);
        categoriaModel = categoriaRepository.save(categoriaModel);

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logService.adicionar(logService.verificarUsuarioLogado(), logRequestDTO, EnumLog.CREATE,
                EnumTipoEntidade.CATEGORIA, "",
                logService.mapearObjetoParaString(categoriaModel));

        return mapper.map(categoriaModel, CategoriaResponseDTO.class);
    }

    // Read publico
    public List<CategoriaResponseDTO> obterTodosPublic() {
        List<Categoria> categoriasModel = categoriaRepository.findAll();
        List<CategoriaResponseDTO> categoriaResponse = new ArrayList<>();

        for (Categoria categoria : categoriasModel) {
            // verifica se a Categoria está ativa.
            if (categoria.getAtivo()) {
                categoriaResponse.add(mapper.map(categoria, CategoriaResponseDTO.class));
            }
        }
        return categoriaResponse;
    }

    public CategoriaResponseDTO obterPorNomePublic(String Nome) {
        Optional<Categoria> categoria = categoriaRepository.findByNome(Nome);
        CategoriaResponseDTO categoriaResponse = new CategoriaResponseDTO();

        if (categoria.isEmpty()) {
            throw new ResourceNotFound("Categoria não foi encontrada na base com o Nome: " + Nome);
        }

        if (categoria.get().getAtivo()) {
            categoriaResponse = mapper.map(categoria.get(), CategoriaResponseDTO.class);
        } else {
            throw new ResourceBadRequest("A Categoria com o nome " + Nome + " está inativa");
        }
        return categoriaResponse;
    }

    public CategoriaResponseDTO obterPorIdPublic(Long id) {
        ChecaValores.verificaValorLong(id);
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        CategoriaResponseDTO categoriaResponse = new CategoriaResponseDTO();

        if (categoria.isEmpty()) {
            throw new ResourceNotFound("Categoria não foi encontrada na base com o Id: " + id);
        }

        if (categoria.get().getAtivo()) {
            categoriaResponse = mapper.map(categoria.get(), CategoriaResponseDTO.class);
        } else {
            throw new ResourceBadRequest("A Categoria com id " + id + " está inativa");
        }
        return categoriaResponse;
    }

    // Read private
    public List<CategoriaResponseDTO> obterTodos() {

        List<Categoria> categorias = categoriaRepository.findAll();

        List<CategoriaResponseDTO> categoriasResponse = new ArrayList<>();

        for (Categoria categoria : categorias) {
            categoriasResponse.add(mapper.map(categoria, CategoriaResponseDTO.class));
        }

        return categoriasResponse;
    }

    public CategoriaResponseDTO obterPorId(Long id) {
        ChecaValores.verificaValorLong(id);
        Optional<Categoria> optCategoria = categoriaRepository.findById(id);

        if (optCategoria.isEmpty()) {
            throw new ResourceNotFound("Não existe uma categoria com o ID " + id);
        }
        return mapper.map(optCategoria.get(), CategoriaResponseDTO.class);
    }

    // Update
    @Transactional
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO categoriaRequest) {
        ChecaValores.verificaValorLong(id);

        Categoria categoriaBase = mapper.map(obterPorId(id), Categoria.class);

        Categoria categoriaModel = mapper.map(categoriaRequest, Categoria.class);

        categoriaModel.setId(id);
        if (categoriaModel.getNome() == null) {
            categoriaModel.setNome(categoriaBase.getNome());
        }
        if (categoriaModel.getObservacao() == null) {
            categoriaModel.setObservacao(categoriaBase.getNome());
        }
        if (categoriaBase.getAtivo() == null) {
            categoriaModel.setAtivo(categoriaBase.getAtivo());
        }

        categoriaModel = categoriaRepository.save(categoriaModel);

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();

        // Verificar se o Produto foi ativado
        if (categoriaBase.getAtivo() != categoriaModel.getAtivo()) {
            // Usando a porra do ternario aqui, se ativo for true ele ACTIVOU, cc ele
            // DESACTIVOU :D
            EnumLog logStatus = categoriaModel.getAtivo() ? EnumLog.ACTIVATE : EnumLog.DEACTIVATE;
            logService.adicionar(logService.verificarUsuarioLogado(), logRequestDTO, logStatus,
                    EnumTipoEntidade.CATEGORIA,
                    logService.mapearObjetoParaString(categoriaBase),
                    logService.mapearObjetoParaString(categoriaModel));
        }

        // Registrar Mudanças UPDATE na Auditoria
        logService.adicionar(logService.verificarUsuarioLogado(), logRequestDTO, EnumLog.UPDATE,
                EnumTipoEntidade.CATEGORIA,
                logService.mapearObjetoParaString(categoriaBase),
                logService.mapearObjetoParaString(categoriaModel));

        return mapper.map(categoriaModel, CategoriaResponseDTO.class);
    }

    // Delete
    public void deletar(Long id) {
        ChecaValores.verificaValorLong(id);
        obterPorId(id);
        categoriaRepository.deleteById(id);

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logService.adicionar(logService.verificarUsuarioLogado(), logRequestDTO, EnumLog.DELETE,
                EnumTipoEntidade.CATEGORIA, "", "");

    }
}
