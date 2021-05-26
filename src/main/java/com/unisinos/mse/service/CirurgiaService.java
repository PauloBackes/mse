package com.unisinos.mse.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.unisinos.mse.entity.CirurgiaEntity;
import com.unisinos.mse.entity.EquipamentoEntity;
import com.unisinos.mse.entity.MaterialEntity;
import com.unisinos.mse.mapper.CirurgiaMapper;
import com.unisinos.mse.model.Cirurgia;
import com.unisinos.mse.repository.CirurgiaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CirurgiaService {

    CirurgiaRepository cirurgiaRepository;

    public List<Cirurgia> buscarTodasCirurgias() {
        return CirurgiaMapper.mapToCirurgiaList(cirurgiaRepository.findAll());
    }

    public Cirurgia buscarCirurgiaPeloId(String id) {
        return CirurgiaMapper.mapToCirurgia(cirurgiaRepository.findCirurgiaById(id));
    }

    public Cirurgia atualizarInstrumentosValidados(String cirurgiaId,
                                                   String[] equipamentosSelecionados,
                                                   String[] materiaisSelecionados) {

        CirurgiaEntity cirurgiaEntity = cirurgiaRepository.findCirurgiaById(cirurgiaId);

        cirurgiaEntity = resetarInstrumentosValidos(cirurgiaEntity);

        cirurgiaEntity = atualizarMateriaisValidados(cirurgiaEntity, materiaisSelecionados);
        cirurgiaEntity = atualizarEquipamentosValidados(cirurgiaEntity, equipamentosSelecionados);

        return atualizarCirurgia(cirurgiaEntity);
    }

    private CirurgiaEntity atualizarMateriaisValidados(CirurgiaEntity cirurgiaEntity, String[] materiais) {
        if (ObjectUtils.isEmpty(materiais)) return cirurgiaEntity;

        for (String materialSelecionado : materiais) {
            for (MaterialEntity materialAtual : cirurgiaEntity.getMaterial()) {
                if (materialSelecionado.equals(materialAtual.getDescricao())) {
                    materialAtual.setValidado(true);
                }
            }
        }
        return cirurgiaEntity;
    }

    private CirurgiaEntity atualizarEquipamentosValidados(CirurgiaEntity cirurgiaEntity, String[] equipamentos) {
        if (ObjectUtils.isEmpty(equipamentos)) return cirurgiaEntity;

        for (String equipamentoSelecionado : equipamentos) {
            for (EquipamentoEntity equipamentoAtual : cirurgiaEntity.getEquipamento()) {
                if (equipamentoSelecionado.equals(equipamentoAtual.getDescricao())) {
                    equipamentoAtual.setValidado(true);
                }
            }
        }
        return cirurgiaEntity;
    }

    private CirurgiaEntity resetarInstrumentosValidos(CirurgiaEntity cirurgiaEntity) {
        cirurgiaEntity.getEquipamento()
                .stream()
                .forEach(equipamentoEntity -> equipamentoEntity.setValidado(false));

        cirurgiaEntity.getMaterial()
                .stream()
                .forEach(materialEntity -> materialEntity.setValidado(false));

        return cirurgiaEntity;
    }

    public Cirurgia atualizarCirurgia(CirurgiaEntity cirurgiaEntity) {
        return CirurgiaMapper.mapToCirurgia(cirurgiaRepository.save(cirurgiaEntity));
    }

    public void gerarPdf(String idCirurgia) {
        try {
            CirurgiaEntity cirurgiaEntity = cirurgiaRepository.findCirurgiaById(idCirurgia);
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter("src/main/java/com/unisinos/mse/pdf/teste.pdf"));
            Document doc = new Document(pdfDoc);

            Table table = new Table(1);

            //getCell("Qtde", TextAlignment.LEFT, fonts.get("courierFont"))

            List<Cell> cabecalho = new ArrayList<>();
            cabecalho.add(new Cell().add(new Paragraph("Instrumentos validados")));
            /*cabecalho.add(new Cell().add(new Paragraph("Material")));
            cabecalho.add(new Cell().add(new Paragraph("Equipamento")));*/
            cabecalho.stream().forEach(cell -> table.addCell(cell));

            List<Cell> corpoTabela = new ArrayList<>();
            cirurgiaEntity.getMaterial().stream()
                    .filter(materialEntity -> materialEntity.getValidado())
                    .map(materialEntity ->
                            corpoTabela.add(new Cell().add(new Paragraph(materialEntity.getDescricao()))))
                    .collect(Collectors.toList());

            cirurgiaEntity.getEquipamento().stream()
                    .filter(equipamentoEntity -> equipamentoEntity.getValidado())
                    .map(equipamentoEntity ->
                            corpoTabela.add(new Cell().add(new Paragraph(equipamentoEntity.getDescricao()))))
                    .collect(Collectors.toList());

            corpoTabela.stream().forEach(cell -> table.addCell(cell));
            doc.add(table);

            //=========================================================================================
            Table table2 = new Table(1);
            cabecalho = new ArrayList<>();
            cabecalho.add(new Cell().add(new Paragraph("Instrumentos não validados")));
            /*cabecalho.add(new Cell().add(new Paragraph("Material")));
            cabecalho.add(new Cell().add(new Paragraph("Equipamento")));*/
            cabecalho.stream().forEach(cell -> table2.addCell(cell));

            List<Cell> corpoTabela2 = new ArrayList<>();
            cirurgiaEntity.getMaterial().stream()
                    .filter(materialEntity -> !materialEntity.getValidado())
                    .map(materialEntity ->
                            corpoTabela2.add(new Cell().add(new Paragraph(materialEntity.getDescricao()))))
                    .collect(Collectors.toList());

            cirurgiaEntity.getEquipamento().stream()
                    .filter(equipamentoEntity -> !equipamentoEntity.getValidado())
                    .map(equipamentoEntity ->
                            corpoTabela2.add(new Cell().add(new Paragraph(equipamentoEntity.getDescricao()))))
                    .collect(Collectors.toList());

            corpoTabela2.stream().forEach(cell -> table2.addCell(cell));
            doc.add(table2);



            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
