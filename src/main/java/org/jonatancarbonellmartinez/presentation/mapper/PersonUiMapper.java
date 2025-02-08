package org.jonatancarbonellmartinez.presentation.mapper;

import javax.inject.Singleton;
import org.jonatancarbonellmartinez.domain.model.Person;
import org.jonatancarbonellmartinez.presentation.viewmodel.PersonViewModel.PersonUI;

@Singleton
public class PersonUiMapper {
    public PersonUI toUiModel(Person domain) {
        PersonUI ui = new PersonUI();
        ui.setId(domain.getId());
        ui.setCode(domain.getCode());
        ui.setRank(domain.getRank());
        ui.setName(domain.getName());
        ui.setLastName1(domain.getLastName1());
        ui.setLastName2(domain.getLastName2());
        ui.setPhone(domain.getPhone());
        ui.setDni(domain.getDni());
        ui.setDivision(domain.getDivision());
        ui.setRole(domain.getRole());
        ui.setOrder(domain.getOrder());
        ui.setActive(domain.isActive());
        return ui;
    }

    public Person toDomain(PersonUI ui) {
        return new Person.Builder()
                .id(ui.getId())
                .code(ui.getCode())
                .rank(ui.getRank())
                .name(ui.getName())
                .lastName1(ui.getLastName1())
                .lastName2(ui.getLastName2())
                .phone(ui.getPhone())
                .dni(ui.getDni())
                .division(ui.getDivision())
                .role(ui.getRole())
                .order(ui.getOrder())
                .isActive(ui.isActive())
                .build();
    }
}