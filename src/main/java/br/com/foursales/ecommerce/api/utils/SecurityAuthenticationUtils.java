package br.com.foursales.ecommerce.api.utils;


import br.com.foursales.ecommerce.api.model.Usuario;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Objects.isNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityAuthenticationUtils {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getIdUsuario() {
        Authentication authentication = getAuthentication();

        if (isNull(authentication)) return null;

        return ((Usuario) authentication.getPrincipal()).getId();
    }
}
