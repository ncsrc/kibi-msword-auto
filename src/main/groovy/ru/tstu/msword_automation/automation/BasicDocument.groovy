package ru.tstu.msword_automation.automation

import groovy.transform.PackageScope
import org.codehaus.groovy.scriptom.ActiveXObject
import org.codehaus.groovy.scriptom.Scriptom
import ru.tstu.msword_automation.automation.constants.FindStrategy
import ru.tstu.msword_automation.automation.constants.ReplacementStrategy
import ru.tstu.msword_automation.automation.constants.SaveFormat
import ru.tstu.msword_automation.automation.constants.SaveOptions

@PackageScope class BasicDocument implements Document
{
    protected final def doc
    protected final def application // protected for testing extended class

    BasicDocument(String name)
    {
        if(!templateFolderIsSet()){
            throw new TempFolderException("Template folder property is not set")
        }
        this.application = new ActiveXObject("Word.Application")
        this.doc = application.Documents.Open(System.getProperty("template_folder") + File.separator + name)
    }

    public void replace(String key, String replacement, ReplacementStrategy strategy)
    {
        def find = getFindObject(key, FindStrategy.CONTINUE)
        setReplacementObject(replacement, find)
        executeReplacement(find, strategy)
    }

    public boolean find(String key, FindStrategy strategy)
    {
        def find = getFindObject(key, strategy)
        return find.Execute()
    }

    public void close(SaveOptions options)
    {
        application.quit(options.value())
    }

    public void save()
    {
        doc.save(); // TODO change save option to format with macro
    }

    public void saveAs(String location, String name, SaveFormat format)
    {
        application.ChangeFileOpenDirectory(location)
        doc.SaveAs(name, format.value())
    }


    private boolean templateFolderIsSet()
    {
        if(System.getProperty("template_folder") == null){
            return false;
        }else{
            return true;
        }
    }


    private def getFindObject(String key, FindStrategy findStrategy)
    {
        def find = application.Selection.Find
        find.ClearFormatting()
        find.Text = key
        find.Forward = true;
        find.Wrap = findStrategy.value();
        return find;
    }

    private void setReplacementObject(String replacement, def findObject)
    {
        def replacementObject = findObject.Replacement
        replacementObject.ClearFormatting()
        replacementObject.Text = replacement
    }

    private void executeReplacement(def findObject, ReplacementStrategy strategy)
    {
        findObject.Execute(Scriptom.MISSING, Scriptom.MISSING, Scriptom.MISSING,
                Scriptom.MISSING, Scriptom.MISSING, Scriptom.MISSING, Scriptom.MISSING,
                Scriptom.MISSING, Scriptom.MISSING, Scriptom.MISSING, strategy.value())
    }




    // TODO replace names in constants to human-readable, place links to ms docs, opt - brief description






}
