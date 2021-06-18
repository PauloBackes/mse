package com.unisinos.mse.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.unisinos.mse.model.Cirurgia;
import com.unisinos.mse.model.Equipamento;
import com.unisinos.mse.model.Material;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Log
public class RelatorioService {

    public File gerarRelatorioCirurgia(Cirurgia cirurgia) {

        try {
            String pasta = "pdf";
            Path path = Paths.get(FileSystemView.getFileSystemView()
                            .getHomeDirectory()
                            .getAbsolutePath(),
                    pasta);
            if (!Files.exists(path)) {
                path.toFile().mkdir();
            }

            String destino = path.toAbsolutePath() + "/relatorio.pdf";
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destino));
            Document documento = new Document(pdfDoc);

            documento.add(tabelaInformacaoesCirurgia(cirurgia));
            documento.add(tabelaInstrumentosValidados(cirurgia));
            documento.add(tabelaInstrumentosNaoValidados(cirurgia));

            documento.close();

            File file = new File(destino);
            log.info("Pdf criado com sucesso");
            return file;
        } catch (Exception e) {
            log.info("Mensagem de erro no processo de criação do pdf: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private Cell celulaVazia() {
        return new Cell();
    }

    private Table tabelaInformacaoesCirurgia(Cirurgia cirurgia) {
        Table tabela = new Table(UnitValue.createPercentArray(new float[]{17, 17}));
        tabela.setWidth(UnitValue.createPercentValue(100));

        var celula = celulaSemBorda("Procedimento", cirurgia.getDescricao(), TextAlignment.LEFT);
        tabela.addCell(celula);

        celula = celulaSemBorda("Id cirurgia", cirurgia.getId().toString(), TextAlignment.RIGHT);
        tabela.addCell(celula);

        celula = celulaSemBorda("Horário previsto de início", cirurgia.getHoraInicio(), TextAlignment.LEFT);
        tabela.addCell(celula);

        celula = celulaSemBorda("Sala", cirurgia.getSala(), TextAlignment.RIGHT);
        tabela.addCell(celula);

        celula = celulaSemBorda("Horário previsto de término",
                Objects.isNull(cirurgia.getHoraTermino()) ? "Não definido" : cirurgia.getHoraTermino(),
                TextAlignment.LEFT);
        tabela.addCell(celula);

        celula = celulaSemBorda("Dia", cirurgia.getDataInicio(), TextAlignment.RIGHT);
        tabela.addCell(celula);

        return tabela;
    }

    private Cell celulaSemBorda(String texto, String conteudo, TextAlignment alinnhamento) {
        Paragraph paragrafo = new Paragraph(String.format("%s: %s", texto, conteudo))
                .setTextAlignment(alinnhamento);

        return new Cell()
                .add(paragrafo)
                .setBorder(Border.NO_BORDER);
    }

    private Cell celulaComBorda(String texto, TextAlignment alinnhamento) {
        Paragraph paragrafo = new Paragraph(String.format("%s", texto))
                .setTextAlignment(alinnhamento);

        return new Cell()
                .add(paragrafo);
    }

    private Table tabelaInstrumentosValidados(Cirurgia cirurgia) {
        Table tabela = new Table(UnitValue.createPercentArray(new float[]{17, 17}));
        tabela.setWidth(UnitValue.createPercentValue(100))
                .setMarginTop(20);

        Cell celula = new Cell(1, 2)
                .add(new Paragraph("Instrumentos validados")
                        .setTextAlignment(TextAlignment.CENTER));

        tabela.addHeaderCell(celula);

        celula = celulaComBorda("Equipamentos", TextAlignment.CENTER);
        tabela.addHeaderCell(celula);

        celula = celulaComBorda("Materiais", TextAlignment.CENTER);
        tabela.addHeaderCell(celula);


        int tamanho = 0;
        List<Equipamento> equipamentosValidados = new ArrayList<>();
        List<Material> materiaisValidados = new ArrayList<>();


        if (!ObjectUtils.isEmpty(cirurgia.getEquipamento()) &&
                !ObjectUtils.isEmpty(cirurgia.getMaterial())) {

            equipamentosValidados = cirurgia.getEquipamento().stream()
                    .filter(Equipamento::getValidado)
                    .collect(Collectors.toList());

            materiaisValidados = cirurgia.getMaterial().stream()
                    .filter(Material::getValidado)
                    .collect(Collectors.toList());

            tamanho = equipamentosValidados.size() > materiaisValidados.size() ?
                    equipamentosValidados.size() : materiaisValidados.size();
        }

        if (tamanho > 0) {

            for (int indice = 0; indice < tamanho; indice++) {
                if (equipamentosValidados.size() > indice &&
                        materiaisValidados.size() > indice) {

                    var celulaComEquipamento = celulaComBorda(equipamentosValidados
                            .get(indice)
                            .getDescricao(), TextAlignment.CENTER);

                    tabela.addCell(celulaComEquipamento);

                    var celulaComMaterial = celulaComBorda(materiaisValidados
                            .get(indice)
                            .getDescricao(), TextAlignment.CENTER);

                    tabela.addCell(celulaComMaterial);

                } else if (equipamentosValidados.size() > indice) {

                    var celulaComEquipamento = celulaComBorda(equipamentosValidados
                            .get(indice)
                            .getDescricao(), TextAlignment.CENTER);

                    tabela.addCell(celulaComEquipamento);

                    tabela.addCell(celulaVazia());

                } else if (materiaisValidados.size() > indice) {

                    tabela.addCell(celulaVazia());

                    var celulaComMaterial = celulaComBorda(materiaisValidados
                            .get(indice)
                            .getDescricao(), TextAlignment.CENTER);

                    tabela.addCell(celulaComMaterial);

                }
            }
        }
        return tabela;
    }

    private Table tabelaInstrumentosNaoValidados(Cirurgia cirurgia) {
        Table tabela = new Table(UnitValue.createPercentArray(new float[]{17, 17}))
                .setMarginTop(20);
        tabela.setWidth(UnitValue.createPercentValue(100));

        Cell celula = new Cell(1, 2).add(new Paragraph("Instrumentos não validados").setTextAlignment(TextAlignment.CENTER));
        tabela.addHeaderCell(celula);

        celula = celulaComBorda("Equipamentos", TextAlignment.CENTER);
        tabela.addHeaderCell(celula);

        celula = celulaComBorda("Materiais", TextAlignment.CENTER);
        tabela.addHeaderCell(celula);

        List<Equipamento> equipamentosNaoValidados = new ArrayList<>();
        List<Material> materiaisNaoValidados = new ArrayList<>();
        int tamanho = 0;

        if (!ObjectUtils.isEmpty(cirurgia.getEquipamento()) &&
                !ObjectUtils.isEmpty(cirurgia.getMaterial())) {

            equipamentosNaoValidados = cirurgia.getEquipamento().stream()
                    .filter(equipamento -> !equipamento.getValidado())
                    .collect(Collectors.toList());

            materiaisNaoValidados = cirurgia.getMaterial().stream()
                    .filter(material -> !material.getValidado())
                    .collect(Collectors.toList());

            tamanho = equipamentosNaoValidados.size() > materiaisNaoValidados.size() ?
                    equipamentosNaoValidados.size() : materiaisNaoValidados.size();

        }

        if (tamanho > 0) {

            for (int indice = 0; indice < tamanho; indice++) {
                if (equipamentosNaoValidados.size() > indice &&
                        materiaisNaoValidados.size() > indice) {

                    var celulaComEquipamento = celulaComBorda(equipamentosNaoValidados
                            .get(indice)
                            .getDescricao(), TextAlignment.CENTER);

                    tabela.addCell(celulaComEquipamento);

                    var celulaComMaterial = celulaComBorda(materiaisNaoValidados
                            .get(indice)
                            .getDescricao(), TextAlignment.CENTER);

                    tabela.addCell(celulaComMaterial);

                } else if (equipamentosNaoValidados.size() > indice) {

                    var celulaComEquipamento = celulaComBorda(equipamentosNaoValidados
                            .get(indice)
                            .getDescricao(), TextAlignment.CENTER);

                    tabela.addCell(celulaComEquipamento);

                    tabela.addCell(celulaVazia());

                } else if (materiaisNaoValidados.size() > indice) {

                    tabela.addCell(celulaVazia());

                    var celulaComMaterial = celulaComBorda(materiaisNaoValidados
                            .get(indice)
                            .getDescricao(), TextAlignment.CENTER);

                    tabela.addCell(celulaComMaterial);

                }
            }
        }
        return tabela;
    }
}

