package br.com.foursales.ecommerce.api.config;

import br.com.foursales.ecommerce.api.model.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(modifyOnCreate = false, dateTimeProviderRef = "dateTimeProvider")
public class PersistenceConfig {

    @Bean
    public AuditorAware<Usuario> createAuditorProvider() {
        return new SecurityAuditor();
    }

    public static class SecurityAuditor implements AuditorAware<Usuario> {

        @NonNull
        @Override
        public Optional<Usuario> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = null;
            if (authentication != null && authentication.getPrincipal() instanceof Usuario usuarioLogado) {
                usuario = usuarioLogado;
            }
            return Optional.ofNullable(usuario);
        }
    }

    @Bean
    DateTimeProvider dateTimeProvider() {
        return CurrentDateTimeProvider.INSTANCE;
    }
}
