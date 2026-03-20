package com.telecom.telecom_app;

import com.telecom.telecom_app.model.Rol;
import com.telecom.telecom_app.model.Usuario;
import com.telecom.telecom_app.model.Vendedor;
import com.telecom.telecom_app.repository.UsuarioRepository;
import com.telecom.telecom_app.repository.VendedorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TelecomAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelecomAppApplication.class, args);
	}

	@Bean
	CommandLineRunner seedData(
			UsuarioRepository usuarioRepo,
			VendedorRepository vendedorRepo,
			PasswordEncoder passwordEncoder
	) {
		return args -> {

			// 1) Crear admin si no existe
			usuarioRepo.findByUsername("admin").orElseGet(() -> {
				Usuario admin = new Usuario();
				admin.setUsername("admin");
				admin.setPasswordHash(passwordEncoder.encode("admin123"));
				admin.setRol(Rol.ADMIN);
				return usuarioRepo.save(admin);
			});

			// 2) Crear vendedor demo + su usuario si no existe
			Usuario vendedorUser = usuarioRepo.findByUsername("vendedor1").orElseGet(() -> {
				Usuario u = new Usuario();
				u.setUsername("vendedor1");
				u.setPasswordHash(passwordEncoder.encode("vend123"));
				u.setRol(Rol.VENDEDOR);
				return usuarioRepo.save(u);
			});

			// Si no existe un vendedor asociado a ese usuario, crearlo
			boolean vendedorExiste = vendedorRepo.findAll().stream()
					.anyMatch(v -> v.getUsuario() != null && v.getUsuario().getId().equals(vendedorUser.getId()));

			if (!vendedorExiste) {
				Vendedor v = new Vendedor();
				v.setNombre("Vendedor Demo");
				v.setTelefono("3000000000");
				v.setEmail("vendedor@demo.com");
				v.setUsuario(vendedorUser);
				vendedorRepo.save(v);
			}
		};
	}
}