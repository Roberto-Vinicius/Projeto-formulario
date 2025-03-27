package com.roberto.form.entity.enums;

public enum Escolariedade {
    GRADUACAO(0),
    MESTRADO(1),
    DOUTORADO(2),
    ENSINO_MEDIO(3),
    ENSINO_FUNDAMENTAL(4);

    private int code;

    private Escolariedade(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }

    public static Escolariedade toEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (Escolariedade value : Escolariedade.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }

        throw new IllegalArgumentException("Código inválido: " + code);
    }
}
