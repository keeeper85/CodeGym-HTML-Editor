package com.codegym.task.task32.task3209;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

public class Controller {

    private View view;
    private HTMLDocument document;
    private File currentFile;

    public static void main(String[] args) {

        View view = new View();
        Controller controller = new Controller(view);
        view.setController(controller);
        view.init();
        controller.init();

    }

    public Controller(View view) {
        this.view = view;
    }

    public void exit(){
        System.exit(0);
    }

    public void init(){
        createNewDocument();
    }

    public HTMLDocument getDocument() {
        return document;
    }

    public void resetDocument(){
        if (document != null){
            document.removeUndoableEditListener(view.getUndoListener());
        }

        HTMLEditorKit kit = new HTMLEditorKit();
        document = (HTMLDocument) kit.createDefaultDocument();
        document.addUndoableEditListener(view.getUndoListener());
        view.update();
    }

    public void setPlainText(String text){
        resetDocument();
        StringReader reader = new StringReader(text);
        HTMLEditorKit kit = new HTMLEditorKit();
        try {
            kit.read(reader, document,0);
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public String getPlainText(){
        StringWriter stringWriter = new StringWriter();
        HTMLEditorKit kit = new HTMLEditorKit();
        try {
            kit.write(stringWriter, document, 0, document.getLength());
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }

        return stringWriter.toString();
    }

    public void createNewDocument(){
        view.selectHtmlTab();
        resetDocument();
        view.setTitle("HTML edtor");
        view.resetUndo();
        currentFile = null;
    }

    public void openDocument(){
        try{
            view.selectHtmlTab();
            HTMLFileFilter fileFilter = new HTMLFileFilter();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(fileFilter);

            int retrn = fileChooser.showOpenDialog(view);
            if(retrn == JFileChooser.APPROVE_OPTION){
                currentFile = fileChooser.getSelectedFile();
                resetDocument();
                view.setTitle(currentFile.getName());

                FileReader reader = new FileReader(currentFile);
                HTMLEditorKit kit = new HTMLEditorKit();
                kit.read(reader, document, 0);
                view.resetUndo();
            }
        }catch (Exception e){
            ExceptionHandler.log(e);
        }

    }

    public void saveDocument(){
        if (currentFile == null) saveDocumentAs();
        else {
            try{
                view.selectHtmlTab();
                FileWriter writer = new FileWriter(currentFile);
                HTMLEditorKit kit = new HTMLEditorKit();
                kit.write(writer,document,0,document.getLength());
                writer.close();
            } catch (Exception e){
                ExceptionHandler.log(e);
            }
        }

    }

    public void saveDocumentAs() {
        try{
            view.selectHtmlTab();
            HTMLFileFilter fileFilter = new HTMLFileFilter();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(fileFilter);

            int retrn = fileChooser.showSaveDialog(view);
            if(retrn == JFileChooser.APPROVE_OPTION){
                currentFile = fileChooser.getSelectedFile();
                view.setTitle(currentFile.getName());

                FileWriter writer = new FileWriter(currentFile);
                HTMLEditorKit kit = new HTMLEditorKit();
                kit.write(writer,document,0,document.getLength());
                writer.close();
            }
        }catch (Exception e){
            ExceptionHandler.log(e);
        }
    }

}
