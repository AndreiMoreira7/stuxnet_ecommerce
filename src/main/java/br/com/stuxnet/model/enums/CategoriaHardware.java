package br.com.stuxnet.model.enums;

public enum CategoriaHardware {
    PROCESSADOR("CPU - Processadores"),
    PLACA_DE_VIDEO("GPU - Placas de Vídeo"),
    PLACA_MAE("Motherboards"),
    MEMORIA_RAM("Memórias RAM DDR4/DDR5"),
    ARMAZENAMENTO("SSDs NVMe / SATA e HDDs"),
    FONTE_ALIMENTACAO("Fontes ATX / SFX"),
    REFRIGERACAO("Water Coolers e Air Coolers");

    private final String descricao;

    CategoriaHardware(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}