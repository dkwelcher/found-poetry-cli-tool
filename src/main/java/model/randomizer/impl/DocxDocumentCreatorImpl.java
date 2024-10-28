package model.randomizer.impl;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import model.randomizer.DocxDocumentCreator;

import java.util.List;

public class DocxDocumentCreatorImpl implements DocxDocumentCreator {
    private List<String> combinations;
    @Override
    public void setCombinations(List<String> combinations) {
        this.combinations = combinations;
    }

    @Override
    public XWPFDocument create() {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        for (String combination : combinations) {
            run.setText(combination);
            run.addBreak();
        }
        return document;
    }
}
