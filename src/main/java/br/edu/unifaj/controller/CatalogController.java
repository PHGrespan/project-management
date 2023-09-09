package br.edu.unifaj.controller;

import br.edu.unifaj.dto.CatalogDto;
import br.edu.unifaj.dto.ResponseDto;
import br.edu.unifaj.entity.Catalog;
import br.edu.unifaj.entity.Project;
import br.edu.unifaj.service.CatalogService;
import br.edu.unifaj.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CatalogController {

    @Autowired
    CatalogService catalogService;

    @JsonView(View.Catalog.class)
    @GetMapping("/projects/{projectId}/catalogs")
    public ResponseEntity<Project> findProjectWithCatalogsByProjectId(@PathVariable(value = "projectId") Long projectId) throws Exception {
        return new ResponseEntity<>(catalogService.findProjectWithCatalogsByProjectId(projectId), HttpStatus.OK);
    }

    @JsonView(View.Catalog.class)
    @PostMapping("/catalogs")
    public ResponseEntity<Catalog> insertCatalog(@Valid @RequestBody CatalogDto dto) throws Exception {
        return new ResponseEntity<>(catalogService.save(dto), HttpStatus.CREATED);
    }
    @JsonView(View.Catalog.class)
    @PutMapping("/catalogs/{id}")
    public ResponseEntity<Catalog> updateCatalog(@PathVariable(value = "id") Long id, @Valid @RequestBody CatalogDto dto) throws Exception {
        return new ResponseEntity<>(catalogService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/catalogs/{id}")
    public ResponseEntity<ResponseDto> deleteCatalogById(@PathVariable(value = "id") Long id){
        catalogService.deleteCatalogById(id);
        return new ResponseEntity<>(new ResponseDto("Catalog and Cards deleted"), HttpStatus.OK);
    }

}
