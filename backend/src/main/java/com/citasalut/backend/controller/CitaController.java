package com.citasalut.backend.controller;


import com.citasalut.backend.dto.CitaRequest;
import com.citasalut.backend.dto.CitaResponse;
import com.citasalut.backend.model.Cita;
import com.citasalut.backend.repository.CitaRepository;
import com.citasalut.backend.service.CitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    //Endpoint para generar los slots disponibles de citas (GET/DISPONIBLES)
    @GetMapping("disponibles")
    public ResponseEntity<List<String>> obtenerSlotsDisponibles(@RequestParam String fecha) {
        LocalDate fechaConsultada =  LocalDate.parse(fecha);

        List<String> slotsDisponibles = citaService.obtenerSlotsDisponibles(fechaConsultada);
        return ResponseEntity.ok(slotsDisponibles);
    }


    //Endpoint para reservar una cita nueva (POST)
    @PostMapping
    public ResponseEntity<CitaResponse> crearCita(@Valid @RequestBody CitaRequest citaRequest, Principal principal) {
       //Nos devuelve el email obtenido de el TOKEN, no el nombre
        String email = principal.getName();

        CitaResponse nuevaCita =  citaService.createCita(citaRequest, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
    }

    //Endpoint Historial de citas (GET)
    @GetMapping
    public ResponseEntity<List<CitaResponse>> obtenerCitas(Principal principal) {
        //Nos devuelve el email obtenido de el TOKEN, no el nombre
        String email = principal.getName();

        List<CitaResponse> misCitas = citaService.listarCitas(email);
        return ResponseEntity.ok(misCitas);
    }

    //(PUT)
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Cita> cancelarCita(@PathVariable Long id, Principal principal) {
        Cita citaCancelada= citaService.cancelarCita(id, principal.getName());
        return ResponseEntity.ok(citaCancelada);
    }

}
