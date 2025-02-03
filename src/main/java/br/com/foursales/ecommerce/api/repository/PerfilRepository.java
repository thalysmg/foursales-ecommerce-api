package br.com.foursales.ecommerce.api.repository;

import br.com.foursales.ecommerce.api.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, String> {
}