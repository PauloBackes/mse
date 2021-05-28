package com.unisinos.mse.examplepdf;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;

public class RoundedBorderCellRenderer extends CellRenderer {
    public RoundedBorderCellRenderer(Cell modelElement) {
        super(modelElement);
    }

    // If renderer overflows on the next area, iText uses getNextRender() method to create a renderer for the overflow part.
    // If getNextRenderer isn't overriden, the default method will be used and thus a default rather than custom
    // renderer will be created
    @Override
    public IRenderer getNextRenderer() {
        return new RoundedBorderCellRenderer((Cell) modelElement);
    }

    @Override
    public void draw(DrawContext drawContext) {
        drawContext.getCanvas().roundRectangle(getOccupiedAreaBBox().getX() + 1.5f, getOccupiedAreaBBox().getY() + 1.5f,
                getOccupiedAreaBBox().getWidth() - 3, getOccupiedAreaBBox().getHeight() - 3, 4);
        drawContext.getCanvas().stroke();
        super.draw(drawContext);
    }
}

