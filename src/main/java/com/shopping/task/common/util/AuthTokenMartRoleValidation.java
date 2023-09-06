package com.shopping.task.common.util;

import com.shopping.task.provider.AuthToken;

public class AuthTokenMartRoleValidation {

    public static boolean martRoleValidation(AuthToken authToken) {
        String role = authToken.getRole();

        boolean roleValidation = role.equals("mart");

        return roleValidation;
    }
}
