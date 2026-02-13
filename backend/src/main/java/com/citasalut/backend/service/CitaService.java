package com.citasalut.backend.service;

import com.citasalut.backend.dto.CitaRequest;
import com.citasalut.backend.dto.CitaResponse;
import com.citasalut.backend.model.Cita;
import com.citasalut.backend.model.Usuario;
import com.citasalut.backend.repository.CitaRepository;
import com.citasalut.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private  final UsuarioRepository usuarioRepository;

    //Reservar-Crear cita
    public CitaResponse createCita(CitaRequest citaRequest, String emailPaciente){
        if (citaRepository.existsByDataHora(citaRequest.getDataHora())){
            throw new RuntimeException("Error: La hora seleccionada ya está ocupada.");
        }

        Usuario usuario = usuarioRepository.findByEmail(emailPaciente)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        //Creación de la cita
        Cita cita = new Cita();
        cita.setDataHora(citaRequest.getDataHora());
        cita.setUsuario(usuario);
        cita.setEspecialidad(citaRequest.getEspecialidad());
        cita.setNombreMedico(citaRequest.getNombreMedico());
        cita.setEstat("Paciente");
        //Guardamos la cita en el repositorio y en la variable final para el return
        Cita citaGuardada= citaRepository.save(cita);

        //Devolvemos la cita convrtida a CitaResponse
        return convertirADTO(citaGuardada);
    }


    //Metodo para convertir una cita en un CitaResponse DTO
    private CitaResponse convertirADTO(Cita cita) {
        CitaResponse citaResponse= new CitaResponse(
                cita.getId(),
                cita.getUsuario().getId(),
                cita.getNombreMedico(),
                cita.getEspecialidad(),
                cita.getDataHora(),
                cita.getEstat()
        ); return  citaResponse;
    }


}
