package org.jonatancarbonellmartinez.presentation.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.jonatancarbonellmartinez.domain.model.Person;
import org.jonatancarbonellmartinez.presentation.viewmodel.PersonViewModel.PersonUI;
import org.jonatancarbonellmartinez.services.DateService;

/**
 * Representa una capa adicional de mapeo específica para la UI (User Interface), lo que aporta varios beneficios importantes:
 * Puede manejar formatos específicos de visualización
 * Puede incluir propiedades adicionales para la UI
 * Puede adaptar tipos de datos para la presentación
 * Puede manejar estados específicos de la UI
 */

@Singleton
public class PersonUiMapper {
    private final DateService dateService;

    @Inject
    public PersonUiMapper(DateService dateService) {
        this.dateService = dateService;
    }
    public PersonUI toUiModel(Person domain) {
        PersonUI ui = new PersonUI();
        ui.setId(domain.getId());
        ui.setCode(domain.getCode());
        ui.setRank(domain.getRank());
        ui.setCuerpo(domain.getCuerpo());
        ui.setEspecialidad(domain.getEspecialidad());
        ui.setName(domain.getName());
        ui.setLastName1(domain.getLastName1());
        ui.setLastName2(domain.getLastName2());
        ui.setPhone(domain.getPhone());
        ui.setDni(domain.getDni());
        ui.setDivision(domain.getDivision());
        ui.setRole(domain.getRole());
        ui.setAntiguedadEmpleo(dateService.convertUTCtoLocalDate(domain.getAntiguedadEmpleo()));
        ui.setFechaEmbarque(dateService.convertUTCtoLocalDate(domain.getFechaEmbarque()));
        ui.setOrder(domain.getOrder());
        ui.setActive(domain.isActive() ? "Activo" : "Inactivo");
        return ui;
    }

    public Person toDomain(PersonUI ui) {
        return new Person.Builder()
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