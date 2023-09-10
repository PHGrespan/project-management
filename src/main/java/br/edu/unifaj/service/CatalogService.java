package br.edu.unifaj.service;

import br.edu.unifaj.dto.CatalogDto;
import br.edu.unifaj.entity.Catalog;
import br.edu.unifaj.entity.Project;
import br.edu.unifaj.mapper.CatalogMapper;
import br.edu.unifaj.repository.CatalogRepository;
import br.edu.unifaj.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CatalogService {

    @Autowired
    CatalogRepository catalogRepository;

    @Autowired
    ProjectRepository projectRepository;

    public Catalog findCatalogWithCardsByCatalogId(Long catalogId) throws Exception {
        return catalogRepository.findById(catalogId).orElseThrow(() -> new Exception("Catalog not found"));
    }

    public Catalog save(CatalogDto dto) throws Exception {
        Catalog newCatalog = CatalogMapper.INSTANCE.catalogDtoToCatalog(dto);

        Project project = projectRepository.findById(newCatalog.getProject().getId()).orElseThrow(() -> new Exception("Project not found"));

        newCatalog.setProject(project);

        return catalogRepository.save(newCatalog);
    }

    public Catalog update(Long id, CatalogDto dto) throws Exception {
        Catalog oldCatalog = catalogRepository.findById(id).orElseThrow(() -> new Exception("Catalog not found"));

        Catalog newCatalog = CatalogMapper.INSTANCE.catalogDtoToCatalog(dto);
        newCatalog.setId(oldCatalog.getId());

        return catalogRepository.save(newCatalog);
    }

    public void deleteCatalogById(Long id) {
        catalogRepository.deleteById(id);
    }
}
