package br.com.stuxnet.model;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import br.com.stuxnet.model.enums.CategoriaHardware;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_componentes_hardware")
public class ComponenteHardware implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String sku;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 80)
    private String fabricante;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CategoriaHardware categoria;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precoBase;

    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @Column(length = 1000)
    private String especificacoesTecnicas;

    @Column(nullable = false)
    private boolean ativo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    // 1. Construtor padrão exigido pela especificação JPA
    public ComponenteHardware() {
        this.dataCadastro = LocalDateTime.now();
        this.ativo = true;
    }

    // 2. Construtor parametrizado completo
    public ComponenteHardware(Long id, String sku, String nome, String fabricante,
                              CategoriaHardware categoria, BigDecimal precoBase,
                              Integer quantidadeEstoque, String especificacoesTecnicas,
                              boolean ativo) {
        this.id = id;
        this.sku = sku;
        this.nome = nome;
        this.fabricante = fabricante;
        this.categoria = categoria;
        this.precoBase = precoBase;
        this.quantidadeEstoque = quantidadeEstoque;
        this.especificacoesTecnicas = especificacoesTecnicas;
        this.ativo = ativo;
        this.dataCadastro = LocalDateTime.now();
    }

    //Construtor privado utilizado pelo Design Pattern Builder
    private ComponenteHardware(Builder builder) {
        this.id = builder.id;
        this.sku = builder.sku;
        this.nome = builder.nome;
        this.fabricante = builder.fabricante;
        this.categoria = builder.categoria;
        this.precoBase = builder.precoBase;
        this.quantidadeEstoque = builder.quantidadeEstoque;
        this.especificacoesTecnicas = builder.especificacoesTecnicas;
        this.ativo = builder.ativo;
        this.dataCadastro = LocalDateTime.now();
    }

    // 3. Getters e Setters com proteção de invariantes básicas
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = (sku != null) ? sku.trim().toUpperCase() : null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public CategoriaHardware getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaHardware categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(BigDecimal precoBase) {
        if (precoBase != null && precoBase.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço base não pode ser negativo.");
        }
        this.precoBase = precoBase;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        if (quantidadeEstoque != null && quantidadeEstoque < 0) {
            throw new IllegalArgumentException("O estoque não pode ser negativo.");
        }
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getEspecificacoesTecnicas() {
        return especificacoesTecnicas;
    }

    public void setEspecificacoesTecnicas(String especificacoesTecnicas) {
        this.especificacoesTecnicas = especificacoesTecnicas;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    // 4. equals e hashCode baseados na chave de negócio (SKU)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponenteHardware that = (ComponenteHardware) o;
        return Objects.equals(sku, that.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku);
    }

    // 5. toString estruturado para logs e auditoria
    @Override
    public String toString() {
        return "ComponenteHardware{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", nome='" + nome + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", categoria=" + categoria +
                ", precoBase=" + precoBase +
                ", quantidadeEstoque=" + quantidadeEstoque +
                ", ativo=" + ativo +
                ", dataCadastro=" + dataCadastro +
                '}';
    }

    // =========================================================================
    // BUILDER PATTERN
    // =========================================================================
    public static class Builder {
        private Long id;
        private String sku;
        private String nome;
        private String fabricante;
        private CategoriaHardware categoria;
        private BigDecimal precoBase;
        private Integer quantidadeEstoque = 0;
        private String especificacoesTecnicas;
        private boolean ativo = true;

        public Builder sku(String sku) {
            this.sku = (sku != null) ? sku.trim().toUpperCase() : null;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder fabricante(String fabricante) {
            this.fabricante = fabricante;
            return this;
        }

        public Builder categoria(CategoriaHardware categoria) {
            this.categoria = categoria;
            return this;
        }

        public Builder precoBase(BigDecimal precoBase) {
            this.precoBase = precoBase;
            return this;
        }

        public Builder quantidadeEstoque(Integer quantidadeEstoque) {
            this.quantidadeEstoque = quantidadeEstoque;
            return this;
        }

        public Builder especificacoesTecnicas(String especificacoesTecnicas) {
            this.especificacoesTecnicas = especificacoesTecnicas;
            return this;
        }

        public Builder ativo(boolean ativo) {
            this.ativo = ativo;
            return this;
        }

        public ComponenteHardware build() {
            if (this.sku == null || this.nome == null || this.precoBase == null) {
                throw new IllegalStateException("SKU, Nome e Preço Base são obrigatórios para construir um ComponenteHardware.");
            }
            return new ComponenteHardware(this);
        }
    }

        public void setDataCadastro(LocalDateTime dataCadastro) {
    this.dataCadastro = dataCadastro;
}
}