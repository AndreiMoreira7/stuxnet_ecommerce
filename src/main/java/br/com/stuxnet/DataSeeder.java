package br.com.stuxnet;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.stuxnet.model.Cliente;
import br.com.stuxnet.model.ComponenteHardware;
import br.com.stuxnet.model.enums.CategoriaHardware;
import br.com.stuxnet.repository.ClienteRepository;
import br.com.stuxnet.repository.ComponenteHardwareRepository;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner initDatabase(ClienteRepository clienteRepo,
                                          ComponenteHardwareRepository hardwareRepo) {
        return args -> {
            if (clienteRepo.count() == 0) {
                Cliente clientePadrao = new Cliente(
                        null,
                        "Cliente Teste StuxNet",
                        "cliente@stuxnet.com.br",
                        "123.456.789-00",
                        "$2a$12$HashSimuladoParaDesenvolvimento"
                );
                clienteRepo.save(clientePadrao);
            }

            if (hardwareRepo.count() == 0) {
                ComponenteHardware gpu = new ComponenteHardware.Builder()
                        .sku("GPU-RTX4070S-ASUS")
                        .nome("ASUS Dual GeForce RTX 4070 Super 12GB")
                        .fabricante("ASUS")
                        .categoria(CategoriaHardware.PLACA_DE_VIDEO)
                        .precoBase(new BigDecimal("4500.00"))
                        .quantidadeEstoque(10)
                        .especificacoesTecnicas("12GB GDDR6X, PCIe 4.0, DLSS 3")
                        .build();

                ComponenteHardware cpu = new ComponenteHardware.Builder()
                        .sku("CPU-R7-7800X3D")
                        .nome("Processador AMD Ryzen 7 7800X3D")
                        .fabricante("AMD")
                        .categoria(CategoriaHardware.PROCESSADOR)
                        .precoBase(new BigDecimal("3200.00"))
                        .quantidadeEstoque(8)
                        .especificacoesTecnicas("8 Cores, 16 Threads, 5.0GHz Boost, 96MB L3 Cache, AM5")
                        .build();

                hardwareRepo.saveAll(List.of(gpu, cpu));
            }
        };
    }
}