package com.unisinos.mse.examplepdf;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.AbstractRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TableRenderer;
import com.unisinos.mse.model.Cirurgia;
import com.unisinos.mse.model.Equipamento;
import com.unisinos.mse.model.Material;

public class Example {

    public static void generatePdf1() {
        try {
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter("src/main/java/com/unisinos/mse/pdf/exemplo3.pdf"));
            Document doc = new Document(pdfDoc);

            // Creates outer table
            Table table = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();

            // Draws header for every nested table.
            // That header will be repeated on every page.
            table.setNextRenderer((IRenderer) new InnerTableRenderer(table, new Table.RowRange(0, 0)));

            Cell cell = new Cell(1, 2).add(new Paragraph("This outer header is repeated on every page"));
            table.addHeaderCell(cell);

            // Creates the first inner table
            Table inner1 = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();

            // Creates an empty header cell for the header content drawn by outer table renderer
            cell = new Cell();
            cell.setHeight(20);
            inner1.addHeaderCell(cell);

            // Creates header cell that will be repeated only on pages, where that table has content
            cell = new Cell().add(new Paragraph("This inner header won't be repeated on every page"));
            inner1.addHeaderCell(cell);

            for (int i = 0; i < 10; i++) {
                inner1.addCell(new Cell().add(new Paragraph("test")));
            }

            cell = new Cell().add(inner1);
            table.addCell(cell.setPadding(0));

            // Creates the second inner table
            Table inner2 = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();

            // Creates an empty header cell for the header content drawn by outer table renderer
            cell = new Cell();
            cell.setHeight(20);
            inner2.addHeaderCell(cell);

            // Creates header cell that will be repeated only on pages, where that table has content
            cell = new Cell().add(new Paragraph("This inner header may be repeated on every page"));
            inner2.addHeaderCell(cell);

            for (int i = 0; i < 35; i++) {
                inner2.addCell("test");
            }

            cell = new Cell().add(inner2);
            table.addCell(cell.setPadding(0));

            doc.add(table);

            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class InnerTableRenderer extends TableRenderer {
        public InnerTableRenderer(Table modelElement, Table.RowRange rowRange) {
            super(modelElement, rowRange);
        }

        protected InnerTableRenderer(Table modelElement) {
            super(modelElement);
        }

        @Override
        public void drawChildren(DrawContext drawContext) {
            super.drawChildren(drawContext);

            for (IRenderer renderer : childRenderers) {
                PdfCanvas canvas = drawContext.getCanvas();
                canvas.beginText();
                Rectangle box = ((AbstractRenderer) renderer).getInnerAreaBBox();
                UnitValue fontSize = this.getPropertyAsUnitValue(Property.FONT_SIZE);
                canvas.moveText(box.getLeft(), box.getTop() - (fontSize.isPointValue() ? fontSize.getValue() : 12f));
                canvas.setFontAndSize(this.getPropertyAsFont(Property.FONT),
                        fontSize.isPointValue() ? fontSize.getValue() : 12f);
                canvas.showText("This inner table header will always be repeated");
                canvas.endText();
                canvas.stroke();
            }
        }

        // If renderer overflows on the next area, iText uses getNextRender() method to create a renderer for the overflow part.
        // If getNextRenderer isn't overriden, the default method will be used and thus a default rather than custom
        // renderer will be created
        @Override
        public IRenderer getNextRenderer() {
            return new InnerTableRenderer((Table) modelElement);
        }
    }

    public static void generatePdf2() {
        try {
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter("src/main/java/com/unisinos/mse/pdf/exemplo2.pdf"));
            Document doc = new Document(pdfDoc);
            Table outertable = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
            Table innertable = new Table(UnitValue.createPercentArray(new float[]{3, 17, 1, 16}));
            innertable.setWidth(UnitValue.createPercentValue(100));

            // first row
            // column 1
            Cell cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            innertable.addCell(cell);

            // column 2
            cell = new Cell().add(new Paragraph("Name"));
            cell.setBorder(Border.NO_BORDER);
            innertable.addCell(cell);

            // column 3
            cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            innertable.addCell(cell);

            // column 4
            cell = new Cell().add(new Paragraph("Signature: "));
            cell.setBorder(Border.NO_BORDER);
            innertable.addCell(cell);

            // spacing
            cell = new Cell(1, 4);
            cell.setHeight(3);
            cell.setBorder(Border.NO_BORDER);
            innertable.addCell(cell);

            // subsequent rows
            for (int i = 1; i < 4; i++) {

                // column 1
               /* cell = new Cell().add(new Paragraph(String.format("%s:", i)));
                cell.setBorder(Border.NO_BORDER);
                innertable.addCell(cell);*/

                cell = new Cell();
                cell.setBorder(Border.NO_BORDER);
                innertable.addCell(cell);

                // column 2
                cell = new Cell();
                innertable.addCell(cell);

                // column 3
                cell = new Cell();
                cell.setBorder(Border.NO_BORDER);
                innertable.addCell(cell);

                // column 4
                cell = new Cell();
                innertable.addCell(cell);

                // spacing
                cell = new Cell(1, 4);
                cell.setHeight(3);
                cell.setBorder(Border.NO_BORDER);
                innertable.addCell(cell);
            }

            // second nested table
            cell = new Cell().add(innertable);
            cell.setNextRenderer(new RoundedBorderCellRenderer(cell));
            cell.setBorder(Border.NO_BORDER);
            cell.setPaddingLeft(8);
            cell.setPaddingTop(8);
            cell.setPaddingRight(8);
            cell.setPaddingBottom(8);
            outertable.addCell(cell);

            // add the table
            doc.add(outertable);

            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generatePdf3(Cirurgia cirurgia) {
        try {
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter("src/main/java/com/unisinos/mse/pdf/exemplo3-2.pdf"));
            Document doc = new Document(pdfDoc);
//===============================================Tabelas com dados da cirurgia==========================
            Table innertable = new Table(UnitValue.createPercentArray(new float[]{17, 17}));
            innertable.setWidth(UnitValue.createPercentValue(100));

            Cell cell = new Cell()
                    .add(new Paragraph("Cirugia: " + cirurgia.getDescricao()))
                    .setBorder(Border.NO_BORDER);
            innertable.addCell(cell);

            cell = new Cell()
                    .add(new Paragraph("Sala: " + cirurgia.getSala()).setTextAlignment(TextAlignment.RIGHT))
                    .setBorder(Border.NO_BORDER);
            innertable.addCell(cell);
            // column 4
            cell = new Cell()
                    .add(new Paragraph("Horário previsto de início: " + cirurgia.getHoraInicio()))
                    .setBorder(Border.NO_BORDER);
            innertable.addCell(cell);

            cell = new Cell()
                    .add(new Paragraph("Dia: " + cirurgia.getData()).setTextAlignment(TextAlignment.RIGHT))
                    .setBorder(Border.NO_BORDER);
            innertable.addCell(cell);

            // column 4
            cell = new Cell()
                    .add(new Paragraph("Horário previsto de término: " + cirurgia.getHoraTermino()))
                    .setBorder(Border.NO_BORDER);
            innertable.addCell(cell);
            doc.add(innertable);

//====================================Tabela com instrumentos validados========================

            innertable = new Table(UnitValue.createPercentArray(new float[]{17, 17}))
                    .setMarginTop(10);
            innertable.setWidth(UnitValue.createPercentValue(100));


            cell = new Cell(1, 2).add(new Paragraph("Instrumentos validados").setTextAlignment(TextAlignment.CENTER));
            innertable.addHeaderCell(cell);

            cell = new Cell().add(new Paragraph("Equipamentos").setTextAlignment(TextAlignment.CENTER));
            innertable.addHeaderCell(cell);

            cell = new Cell().add(new Paragraph("Materiais").setTextAlignment(TextAlignment.CENTER));
            innertable.addHeaderCell(cell);

            int tamanho = cirurgia.getEquipamento().size() > cirurgia.getMaterial().size() ?
                    cirurgia.getEquipamento().size() : cirurgia.getMaterial().size();

            for (int indice = 0; indice < tamanho; indice++) {

                if (cirurgia.getEquipamento().size() > indice &&
                        equipamentoValidado(cirurgia.getEquipamento().get(indice))) {
                    cell = new Cell()
                            .add(new Paragraph(cirurgia.getEquipamento().get(indice).getDescricao())
                                    .setTextAlignment(TextAlignment.CENTER));
                    innertable.addCell(cell);

                } else {
                    cell = new Cell();
                    innertable.addCell(cell);
                }

                if (cirurgia.getMaterial().size() > indice &&
                        materialValidado(cirurgia.getMaterial().get(indice))) {

                    cell = new Cell()
                            .add(new Paragraph(cirurgia.getMaterial().get(indice).getDescricao())
                                    .setTextAlignment(TextAlignment.CENTER));
                    innertable.addCell(cell);
                } else {
                    cell = new Cell();
                    innertable.addCell(cell);
                }
            }
            doc.add(innertable);

            /*TODO corrigir a adição de células da tabela, para que elas só sejam postas se
            material ou equipamento tiver algum conteúdo*/

//====================================Tabela com instrumentos não validados========================

            innertable = new Table(UnitValue.createPercentArray(new float[]{17, 17}))
                    .setMarginTop(10);
            innertable.setWidth(UnitValue.createPercentValue(100));


            cell = new Cell(1, 2).add(new Paragraph("Instrumentos validados").setTextAlignment(TextAlignment.CENTER));
            innertable.addHeaderCell(cell);

            cell = new Cell().add(new Paragraph("Equipamentos").setTextAlignment(TextAlignment.CENTER));
            innertable.addHeaderCell(cell);

            cell = new Cell().add(new Paragraph("Materiais").setTextAlignment(TextAlignment.CENTER));
            innertable.addHeaderCell(cell);

            tamanho = cirurgia.getEquipamento().size() > cirurgia.getMaterial().size() ?
                    cirurgia.getEquipamento().size() : cirurgia.getMaterial().size();

            for (int indice = 0; indice < tamanho; indice++) {

                if (cirurgia.getEquipamento().size() > indice) {
                        if(!equipamentoValidado(cirurgia.getEquipamento().get(indice))) {
                            cell = new Cell()
                                    .add(new Paragraph(cirurgia.getEquipamento().get(indice).getDescricao())
                                            .setTextAlignment(TextAlignment.CENTER));
                            innertable.addCell(cell);
                        }

                } else {
                    cell = new Cell();
                    innertable.addCell(cell);
                }

                if (cirurgia.getMaterial().size() > indice) {
                        if(!materialValidado(cirurgia.getMaterial().get(indice))) {

                            cell = new Cell()
                                    .add(new Paragraph(cirurgia.getMaterial().get(indice).getDescricao())
                                            .setTextAlignment(TextAlignment.CENTER));
                            innertable.addCell(cell);
                        }
                } else {
                    cell = new Cell();
                    innertable.addCell(cell);
                }
            }
            doc.add(innertable);
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Boolean equipamentoValidado(Equipamento equipamento) {
        return equipamento.getValidado();
    }

    private static Boolean materialValidado(Material material) {
        return material.getValidado();
    }

}
