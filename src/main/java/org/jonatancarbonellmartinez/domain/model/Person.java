package org.jonatancarbonellmartinez.domain.model;

/**
 * En la capa de dominio, contiente la logica de negocio, los modelos suelen ser POJOs puros sin dependencias de JavaFX.
 */

// Domain Layer (business logic)
public class Person {
    private final Integer id;
    private final String code;
    private final String rank;
    private final String cuerpo;
    private final String especialidad;
    private final String name;
    private final String lastName1;
    private final String lastName2;
    private final String phone;
    private final String dni;
    private final String division;
    private final String role;
    private final Long   antiguedadEmpleo;
    private final Long   fechaEmbarque;
    private final Integer order;
    private final Boolean isActive;

    private Person(Builder builder) {
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
    public Long getAntiguedadEmpleo() { return antiguedadEmpleo; }
    public Long getFechaEmbarque() { return fechaEmbarque; }
    public Integer getOrder() { return order; }
    public Boolean isActive() { return isActive; }

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
        private Long antiguedadEmpleo;
        private Long fechaEmbarque;
        private Integer order;
        private Boolean isActive;

        public Builder() {}

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

        public Builder antiguedadEmpleo(Long antiguedadEmpleo) {
            this.antiguedadEmpleo = antiguedadEmpleo;
            return this;
        }

        public Builder fechaEmbarque(Long fechaEmbarque) {
            this.fechaEmbarque = fechaEmbarque;
            return this;
        }

        public Builder order(Integer order) {
            this.order = order;
            return this;
        }

        public Builder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
