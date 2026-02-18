package com.citasalut.backend.service;

import com.citasalut.backend.dto.CitaRequest;
import com.citasalut.backend.dto.CitaResponse;
import com.citasalut.backend.model.Cita;
import com.citasalut.backend.model.Usuario;
import com.citasalut.backend.repository.CitaRepository;
import com.citasalut.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private  final UsuarioRepository usuarioRepository;


    //Generar horario
    public List<String> generarHorarioHospital() {
        List<String> slots = new ArrayList<>();
        LocalTime hora = LocalTime.of(9, 0);
        while (hora.isBefore(LocalTime.of(20, 0))) {
            slots.add(hora.toString()); // Guarda 09:00
            hora = hora.plusMinutes(30);
        }
        return slots;
    }

    //Reservar-Crear cita//
    public CitaResponse createCita(CitaRequest citaRequest, String emailPaciente){
        if (citaRepository.existsByDataHoraAndEspecialidad(citaRequest.getDataHora(), citaRequest.getEspecialidad())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error: Esta especialidad ya tiene una cita a esta hora.");
        }

        Usuario usuario = usuarioRepository.findByEmail(emailPaciente)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        //Creación de la cita
        Cita cita = new Cita();
        cita.setDataHora(citaRequest.getDataHora());
        cita.setUsuario(usuario);
        cita.setEspecialidad(citaRequest.getEspecialidad());
        cita.setNombreMedico(citaRequest.getNombreMedico());
        cita.setEstat("PENDIENTE");
        //Guardamos la cita en el repositorio y en la variable final para el return
        Cita citaGuardada = citaRepository.save(cita);

        // Devolvemos la cita convertida a CitaResponse
        return convertirADTO(citaGuardada);
    }


    //Metodo para litar citas de un usuario (ya reservadas)//
    public List<CitaResponse> listarCitas(String emailPaciente){
    Usuario usuario = usuarioRepository.findByEmail(emailPaciente)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    List<Cita> citas = citaRepository.findByUsuario_IdOrderByDataHoraAsc(usuario.getId());
    List<CitaResponse> listaFinal = new ArrayList<>();

        for (Cita cita : citas) {

            CitaResponse dto = convertirADTO(cita);
            listaFinal.add(dto);
        }
        return listaFinal;
    }


    //Metodo para generar sltos de citas libres(30 min)//
    public List<String> obtenerSlotsDisponibles(LocalDate fecha, String especialidad){
        LocalDateTime inicioDia = fecha.atStartOfDay();
        LocalDateTime finDia = fecha.atTime(LocalTime.MAX);

        //guarddamos en la lista las citas reservadas
        List<Cita> citasOcupadas = citaRepository.findByDataHoraBetweenAndEspecialidad(inicioDia, finDia, especialidad );

        List<LocalTime> horasOcupadas = new ArrayList<>();
        for (Cita cita : citasOcupadas) {
            horasOcupadas.add(cita.getDataHora().toLocalTime());
        }


        List<String> todosLosSlots = generarHorarioHospital();
        List<String> disponibles = new ArrayList<>();

        LocalTime ahora = LocalTime.now();
        LocalDate hoy = LocalDate.now();

        for (int i = 0; i < todosLosSlots.size(); i++) {
            String slotString = todosLosSlots.get(i); //
            LocalTime horaSlot = LocalTime.parse(slotString);

            //saltamos la horas pasadas
            if (fecha.equals(hoy) && horaSlot.isBefore(ahora)) {
                continue;
            }
            //comprobamos que la hora no este reservada ya
            if(!horasOcupadas.contains(horaSlot)){
                disponibles.add(slotString);
            }
        }
        return disponibles;
    }

    //Cancelar citas
    public Cita cancelarCita(Long idCita, String emailUsuario){
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        if (!cita.getUsuario().getEmail().equals(emailUsuario)){
            throw new RuntimeException("No tienes permisos");
        }
        if (!"PENDIENTE".equals(cita.getEstat())){
            throw new RuntimeException("Solo se pueden cancelar citas pendientes.");
        }
        cita.setEstat("CANCELADA");
        return citaRepository.save(cita);
    }



    //Metodo para convertir una cita en un CitaResponse DTO//
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
