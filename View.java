package com.codegym.task.task32.task3209;

import com.codegym.task.task32.task3209.listeners.FrameListener;
import com.codegym.task.task32.task3209.listeners.TabbedPaneChangeListener;
import com.codegym.task.task32.task3209.listeners.UndoListener;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {

    private Controller controller;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JTextPane htmlTextPane = new JTextPane();
    private JEditorPane plainTextPane = new JEditorPane();
    private UndoManager undoManager = new UndoManager();
    private UndoListener undoListener = new UndoListener(undoManager);

    public View() {

        try{
//            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
//            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e){
            ExceptionHandler.log(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        switch (actionEvent.getActionCommand()){
            case "New":
                controller.createNewDocument();
                break;
            case "Open":
                controller.openDocument();
                break;
            case "Save":
                controller.saveDocument();
                break;
            case "Save as...":
                controller.saveDocumentAs();
                break;
            case "Exit":
                controller.exit();
                break;
            case "About":
                this.showAbout();
                break;
        }

    }

    public boolean canUndo(){
        return undoManager.canUndo();
    }

    public boolean canRedo(){
        return undoManager.canRedo();
    }

    public boolean isHtmlTabSelected() {
        return tabbedPane.getSelectedIndex() == 0;
    }

    public void selectHtmlTab(){
        tabbedPane.setSelectedIndex(0);
        resetUndo();
    }

    public void init(){
        initGui();
        FrameListener frameListener = new FrameListener(this);
        addWindowListener(frameListener);
        setVisible(true);
    }

    public void update(){
        htmlTextPane.setDocument(controller.getDocument());
    }

    public void showAbout(){
        JOptionPane.showMessageDialog(this,"about", "info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void initMenuBar(){

        JMenuBar jMenuBar = new JMenuBar();
        MenuHelper.initFileMenu(this, jMenuBar);
        MenuHelper.initEditMenu(this, jMenuBar);
        MenuHelper.initStyleMenu(this, jMenuBar);
        MenuHelper.initAlignMenu(this, jMenuBar);
        MenuHelper.initColorMenu(this, jMenuBar);
        MenuHelper.initFontMenu(this, jMenuBar);
        MenuHelper.initHelpMenu(this, jMenuBar);

        getContentPane().add(jMenuBar, BorderLayout.NORTH);

    }

    public void initEditor(){
        htmlTextPane.setContentType("text/html");
        JScrollPane jScrollPane = new JScrollPane(htmlTextPane);
        tabbedPane.add("HTML", jScrollPane);

        JScrollPane component = new JScrollPane(plainTextPane);
        tabbedPane.add("Text", component);
        tabbedPane.setPreferredSize(getPreferredSize());

        TabbedPaneChangeListener listener = new TabbedPaneChangeListener(this);
        tabbedPane.addChangeListener(listener);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

    }

    public void initGui(){
        initMenuBar();
        initEditor();
        pack();
    }

    public void selectedTabChanged(){
        int selectedTabId = tabbedPane.getSelectedIndex();

        if (selectedTabId == 0){
            controller.setPlainText(plainTextPane.getText());
        }
        if (selectedTabId == 1){
            plainTextPane.setText(controller.getPlainText());
        }
        resetUndo();
    }



    public void undo(){
        try{
            undoManager.undo();
        } catch (Exception e){
            ExceptionHandler.log(e);
        }

    }

    public void redo(){
        try{
            undoManager.redo();
        } catch (Exception e){
            ExceptionHandler.log(e);
        }
    }

    public void resetUndo(){
        undoManager.discardAllEdits();
    }

    public void exit(){
        controller.exit();
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public UndoListener getUndoListener() {
        return undoListener;
    }
}
