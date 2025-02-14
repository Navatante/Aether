package org.jonatancarbonellmartinez.presentation.model;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class PersonUI {
    // Estos campos solo son de lectura y no participan en bindings
    private Integer id;
    private String code;
    private String rank;
    private String cuerpo;
    private String especialidad;
    private String name;
    private String lastName1;
    private String lastName2;
    private String phone;
    private String dni;
    private String division;
    private String role;
    private LocalDate antiguedadEmpleo;
    private LocalDate fechaEmbarque;
    private Integer order;
    private String isActive;

    private PersonUI(Builder builder) {
        this.id = builder.id;
        this.code = builder.code;
        this.rank = builder.rank;
        this.cuerpo = builder.cuerpo;
        this.especialidad = builder.especialidad;
        this.name = builder.name;
        this.lastName1 = builder.lastName1;
        this.lastName2 = builder.lastName2;
        this.phone = builder.phone;
        this.dni = builder.dni;
        this.division = builder.division;
        this.role = builder.role;
        this.antiguedadEmpleo = builder.antiguedadEmpleo;
        this.fechaEmbarque = builder.fechaEmbarque;
        this.order = builder.order;
        this.isActive = builder.isActive;
    }

    // Getters solo - objeto inmutable
    public Integer getId() { return id; }
    public String getCode() { return code; }
    public String getRank() { return rank; }
    public String getCuerpo() { return cuerpo; }
    public String getEspecialidad() { return especialidad; }
    public String getName() { return name; }
    public String getLastName1() { return lastName1; }
    public String getLastName2() { return lastName2; }
    public String getPhone() { return phone; }
    public String getDni() { return dni; }
    public String getDivision() { return division; }
    public String getRole() { return role; }
    public LocalDate getAntiguedadEmpleo() { return antiguedadEmpleo; }
    public LocalDate getFechaEmbarque() { return fechaEmbarque; }
    public Integer getOrder() { return order; }
    public String isActive() { return isActive; }

    // Builder pattern
    public static class Builder {
        private Integer id;
        private String code;
        private String rank;
        private String cuerpo;
        private String especialidad;
        private String name;
        private String lastName1;
        private String lastName2;
        private String phone;
        private String dni;
        private String division;
        private String role;
        private LocalDate antiguedadEmpleo;
        private LocalDate fechaEmbarque;
        private Integer order;
        private String isActive;

        public Builder() {}

        // Getters and Setters
        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder rank(String rank) {
            this.rank = rank;
            return this;
        }

        public Builder cuerpo(String cuerpo) {
            this.cuerpo = cuerpo;
            return this;
        }

        public Builder especialidad(String especialidad) {
            this.especialidad = especialidad;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder lastName1(String lastName1) {
            this.lastName1 = lastName1;
            return this;
        }

        public Builder lastName2(String lastName2) {
            this.lastName2 = lastName2;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder dni(String dni) {
            this.dni = dni;
            return this;
        }

        public Builder division(String division) {
            this.division = division;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder antiguedadEmpleo(LocalDate antiguedadEmpleo) {
            this.antiguedadEmpleo = antiguedadEmpleo;
            return this;
        }

        public Builder fechaEmbarque(LocalDate fechaEmbarque) {
            this.fechaEmbarque = fechaEmbarque;
            return this;
        }

        public Builder order(Integer order) {
            this.order = order;
            return this;
        }

        public Builder isActive(String isActive) {
            this.isActive = isActive;
            return this;
        }

        public PersonUI build() {
            return new PersonUI(this);
        }
    }
}
