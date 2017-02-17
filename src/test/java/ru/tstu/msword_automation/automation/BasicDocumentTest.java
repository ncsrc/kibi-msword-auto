package ru.tstu.msword_automation.automation;

import com.jacob.com.LibraryLoader;
import org.junit.*;
import org.junit.Test;
import ru.tstu.msword_automation.automation.constants.FindStrategy;
import ru.tstu.msword_automation.automation.constants.ReplacementStrategy;
import ru.tstu.msword_automation.automation.constants.SaveFormat;
import ru.tstu.msword_automation.automation.constants.SaveOptions;

import static org.junit.Assert.*;

public class BasicDocumentTest
{
	private TestableBasicDocument doc;

	@Before
	public void init()
	{
		System.setProperty("template_folder", ".");
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, ".\\jacob-1.14.3-x64.dll"); // in resources
//		System.setProperty("java.library.path", "jacob-1.14.3-x64.dll");
		doc = new TestableBasicDocument("tmp_doc.docx");
	}

	@After
	public void closeDoc()
	{
		doc.close(SaveOptions.DO_NOT_SAVE);
	}


	@Test
	public void CorrectNameFromDefinedInstanceOfDocument()
	{
		assertEquals("tmp_doc.docx", doc.getName());
	}

	@Test
	public void Finding()
	{
		int count = findingCount("asdf");
		assertEquals(4, count);
	}

	@Test
	public void ReplacingAllOccurrences()
	{
		String replacement = "fdsa";
		doc.replace("asdf", replacement, ReplacementStrategy.REPLACE_ALL);
		int count = findingCount(replacement);
		assertEquals(4, count);
	}

	@Test
	public void ChangesSavedWhenDocClosedAndSaved()
	{
		String replacement = "fdsa";
		doc.replace("asdf", replacement, ReplacementStrategy.REPLACE_ALL);
		doc.close(SaveOptions.SAVE);
		doc = new TestableBasicDocument("tmp_doc.docx");
		int count = findingCount(replacement);

		// reversing
		doc.replace(replacement, "asdf", ReplacementStrategy.REPLACE_ALL);
		doc.save();

		assertEquals(4, count);
	}

	@Test
	public void CorrectPathWhenDocWasSavedAs()
	{
		doc.saveAs("C:\\Users\\user\\Desktop", "tmp_doc_test.docx", SaveFormat.DOCX);
		assertEquals("C:\\Users\\user\\Desktop\\tmp_doc_test.docx", doc.getFullPath());
	}

	@Test(expected = TempFolderException.class)
	public void FiresExceptionWhenTempFolderIsNotSet()
	{
		System.clearProperty("template_folder");
		doc = new TestableBasicDocument("tmp_doc.docx");
	}


	private int findingCount(String key)
	{
		int count = 0;
		while (doc.find(key, FindStrategy.STOP)){
			++count;
		}

		return count;
	}



	// TODO test running macro in saved as docx without macros



	// TODO joint compilation in testing scope




}