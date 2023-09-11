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

    public Catalog insert(CatalogDto dto) throws Exception {
        Catalog newCatalog = CatalogMapper.INSTANCE.catalogDtoToCatalog(dto);
        Project project = projectRepository.findById(newCatalog.getProject().getId()).orElseThrow(() -> new Exception("Project not found"));

        if (newCatalog.getProjectPosition() > project.getCatalogs().size() + 1) {
            throw new Exception("Catalog must be in projectPosition between " + 1 + " and " + (project.getCatalogs().size() + 1));
        }

        catalogRepository.updateAllCatalogsIncrementProjectPositionByProjectIdAndProjectPositionBetween(project.getId(), newCatalog.getProjectPosition(), catalogRepository.findMaxProjectPosition(project.getId()).get(0), 1);

        newCatalog.setProject(project);

        return catalogRepository.save(newCatalog);
    }

    public Catalog update(Long id, CatalogDto dto) throws Exception {
        Project project =projectRepository.findById(dto.getIdProject()).orElseThrow(() -> new Exception("Project not found"));
        Catalog oldCatalog = catalogRepository.findById(id).orElseThrow(() -> new Exception("Catalog not found"));

        Catalog newCatalog = CatalogMapper.INSTANCE.catalogDtoToCatalog(dto);

        // Validations
        // Same Catalog
        if ((newCatalog.getProject().getId().equals(oldCatalog.getProject().getId()) && newCatalog.getProjectPosition() > project.getCatalogs().size())) {
            throw new Exception("Catalog must be in catalogPosition between " + 1 + " and " + (project.getCatalogs().size()));

            // Different Catalog
        } else if (newCatalog.getProjectPosition() > project.getCatalogs().size() + 1) {
            throw new Exception("Catalog must be in catalogPosition between " + 1 + " and " + (project.getCatalogs().size() + 1));
        }

        newCatalog.setId(oldCatalog.getId());
        Integer oldProjectPosition = oldCatalog.getProjectPosition();
        Integer newProjectPosition = newCatalog.getProjectPosition();

        oldCatalog.setProjectPosition(0);
        catalogRepository.save(oldCatalog);

        // Same Project
        if (newCatalog.getProject().getId().equals(oldCatalog.getProject().getId())) {

            // Change projectPosition
            if (!newProjectPosition.equals(oldProjectPosition)) {

                // new projectPosition > old projectPosition
                if (newProjectPosition > oldProjectPosition) {
                    // Decreases 1 in the projectPosition of the Catalogs that should be after the old Catalog and before the new Catalog
                    catalogRepository.updateAllCatalogsDecrementProjectPositionByProjectIdAndProjectPositionBetween(newCatalog.getProject().getId(), oldProjectPosition + 1, newProjectPosition, 1);

                    // new projectPosition < old projectPosition
                } else {
                    // Increments 1 in the projectPosition of the Catalogs that should be after the old Catalog and before the new Catalog
                    catalogRepository.updateAllCatalogsIncrementProjectPositionByProjectIdAndProjectPositionBetween(newCatalog.getProject().getId(), newProjectPosition, oldProjectPosition - 1, 1);
                }
            }

            // Different Project
        } else {
            // Decreases 1 in the projectPosition of Catalogs that have projectPosition before the old Catalog
            catalogRepository.updateAllCatalogsDecrementProjectPositionByProjectIdAndProjectPositionBetween(oldCatalog.getProject().getId(), oldProjectPosition + 1, catalogRepository.findMaxProjectPosition(oldCatalog.getProject().getId()).get(0), 1);
            // Increments 1 in the projectPosition of the Catalogs that should be after the new Catalog
            catalogRepository.updateAllCatalogsIncrementProjectPositionByProjectIdAndProjectPositionBetween(newCatalog.getProject().getId(), dto.getProjectPosition(), catalogRepository.findMaxProjectPosition(newCatalog.getProject().getId()).get(0), 1);
        }

        return catalogRepository.save(newCatalog);
    }

    public void deleteCatalogById(Long id) throws Exception {
        Catalog catalog = catalogRepository.findById(id).orElseThrow(() -> new Exception("Catalog not found"));
        catalogRepository.deleteById(id);
        catalogRepository.updateAllCatalogsDecrementProjectPositionByProjectIdAndProjectPositionBetween(catalog.getProject().getId(), 2, catalog.getProjectPosition() + 1, 1);
    }
}
