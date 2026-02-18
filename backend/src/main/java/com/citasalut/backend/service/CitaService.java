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


    //Reservar-Crear cita//
    public CitaResponse createCita(CitaRequest citaRequest, String emailPaciente){
        if (citaRepository.existsByDataHora(citaRequest.getDataHora())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error: L'hora seleccionada ja està ocupada.");
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
        Cita citaGuardada= citaRepository.save(cita);

        //Devolvemos la cita convrtida a CitaResponse
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
    public List<String> obtenerSlotsDisponibles(LocalDate fecha){
        LocalTime horaInicio = LocalTime.of(9, 0);
        LocalTime horaFin = LocalTime.of(20, 0);
        int duracionCita = 30; //Minutos

        //Buscar citas ya resrvadas
        LocalDateTime inicioDia = fecha.atStartOfDay(); //00:00:00 del dia
        LocalDateTime finDia = fecha.atTime(LocalTime.MAX); // 23:59:59 del dia

        //guarddamos en la lista las citas reservadas
        List<Cita> citasOcupadas = citaRepository.findByDataHoraBetween(inicioDia, finDia);

        //De la lista con las citas sacamos las horas de inicio de cada una
        List<LocalTime> horasOcupadas = new ArrayList<>();
        for (Cita cita : citasOcupadas) {
            horasOcupadas.add(cita.getDataHora().toLocalTime());
        }

        //Creamos la lista de slots y la variable para ir sumando 30 min (horaAddCita)
        List<String> slotsDisponibles = new ArrayList<>();
        LocalTime horaAddCita = horaInicio;

        //Usamos la hora actual del usuario para no mostrar las citas del dia que ya han pasado
        LocalTime horaAhora = LocalTime.now();

        while (horaAddCita.isBefore(horaFin)) {

            //sumamos +30 hasta estar en el "presente"
            if (horaAddCita.isBefore(horaAhora)) {
                horaAddCita = horaAddCita.plusMinutes(duracionCita);
                continue; //Continue da otra vuelta al bucle desde el principio
            }

            //comprobamos que la hora de horaAddCita no esta dentro de horasOcupadas y la añadimos a slotsDisponibles
            if(!horasOcupadas.contains(horaAddCita)){
                slotsDisponibles.add(horaAddCita.toString());
            }
            //Sumamos 30
            horaAddCita = horaAddCita.plusMinutes(duracionCita);
        }
        //devolvemos la lista de Strings con las horas disponibles
        return slotsDisponibles;
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
