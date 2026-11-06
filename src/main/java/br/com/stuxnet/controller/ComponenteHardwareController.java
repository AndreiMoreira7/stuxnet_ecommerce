
package br.com.stuxnet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.stuxnet.dto.ComponenteHardwareRequestDTO;
import br.com.stuxnet.dto.ComponenteHardwareResponseDTO;
import br.com.stuxnet.model.enums.CategoriaHardware;
import br.com.stuxnet.service.ComponenteHardwareService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/componentes")
public class ComponenteHardwareController {

    private final ComponenteHardwareService service;

    public ComponenteHardwareController(
            ComponenteHardwareService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ComponenteHardwareResponseDTO> cadastrar(
            @Valid @RequestBody ComponenteHardwareRequestDTO dto) {

        ComponenteHardwareResponseDTO response =
                service.cadastrar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ComponenteHardwareResponseDTO>> listarTodos() {

        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ComponenteHardwareResponseDTO>> listarPorCategoria(
            @PathVariable CategoriaHardware categoria) {

        return ResponseEntity.ok(
                service.listarPorCategoria(categoria)
        );
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> desativar(
            @PathVariable String sku) {

        service.desativarPorSku(sku);

        return ResponseEntity.noContent().build();
    }
}
