package com.spring.security.services.models.validations;

import com.spring.security.persistence.entities.UserEntity;
import com.spring.security.services.models.dtos.ResponseDTO;

public class UserValidation {

    public ResponseDTO validate(UserEntity user) {
        ResponseDTO response = new ResponseDTO();
        response.setNumOfErrors(0);

        if (user.getFirstName().isEmpty() ||
                user.getFirstName().length() < 3 ||
                user.getFirstName().length() > 15) {
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("El campo del nombre no puede ser vacío, tambíen tiene que ser" +
                    " mayor a 3 carácteres y menor a 15.");
        }

        if (user.getLastName().isEmpty() ||
                user.getLastName().length() < 3 ||
                user.getLastName().length() > 30) {
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("El campo apellido no puede ser vacío, también tiene que ser" +
                    " mayor a 3 carácteres y menor a 30.");
        }

        if (user.getEmail().isEmpty() ||
                !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("El campo email no es valido.");
        }

        if (user.getPassword().isEmpty() ||
                !user.getPassword()
                        .matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")) {
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("La contraseña debe tener entre 8 y 16 carácteres, al menos un " +
                    "número, una minúscula y una mayúscula");
        }

        return response;
    }

}
