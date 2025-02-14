package org.jonatancarbonellmartinez.presentation.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.jonatancarbonellmartinez.domain.model.PersonDomain;
import org.jonatancarbonellmartinez.presentation.model.PersonUI;
import org.jonatancarbonellmartinez.services.DateService;
import java.time.LocalDate;

/**
 * Representa una capa adicional de mapeo específica para la UI (User Interface), lo que aporta varios beneficios importantes:
 * Puede manejar formatos específicos de visualización
 * Puede incluir propiedades adicionales para la UI
 * Puede adaptar tipos de datos para la presentación
 * Puede manejar estados específicos de la UI
 */

@Singleton
public class PersonDomainUiMapper {
    private final DateService dateService;

    @Inject
    public PersonDomainUiMapper(DateService dateService) {
        this.dateService = dateService;
    }
    public PersonUI toUiModel(PersonDomain domain) {
        return new PersonUI.Builder()
                .id(domain.getId())
                .code(domain.getCode())
                .rank(domain.getRank())
                .cuerpo(domain.getCuerpo())
                .especialidad(domain.getEspecialidad())
                .name(domain.getName())
                .lastName1(domain.getLastName1())
                .lastName2(domain.getLastName2())
                .phone(domain.getPhone())
                .dni(domain.getDni())
                .division(domain.getDivision())
                .role(domain.getRole())
                .antiguedadEmpleo(dateService.convertUTCtoLocalDate(domain.getAntiguedadEmpleo()))
                .fechaEmbarque(dateService.convertUTCtoLocalDate(domain.getFechaEmbarque()))
                .order(domain.getOrder())
                .isActive(domain.isActive() ? "Activo" : "Inactivo")
                .build();
    }

    public PersonDomain toDomain(PersonUI ui) {
        return new PersonDomain.Builder()
                .id(ui.getId())
                .code(ui.getCode())
                .rank(ui.getRank())
                .cuerpo(ui.getCuerpo())
                .especialidad(ui.getEspecialidad())
                .name(ui.getName())
                .lastName1(ui.getLastName1())
                .lastName2(ui.getLastName2())
                .phone(ui.getPhone())
                .dni(ui.getDni())
                .division(ui.getDivision())
                .role(ui.getRole())
                .fechaEmbarque(dateService.convertLocalToUTCDate(ui.getFechaEmbarque()))
                .antiguedadEmpleo(dateService.convertLocalToUTCDate(ui.getAntiguedadEmpleo()))
                .order(ui.getOrder())
                .isActive(ui.isActive().equals("Activo") ? true : false)
                .build();
    }
}