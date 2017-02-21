package ru.tstu.msword_automation.automation;

import com.jacob.com.LibraryLoader;
import org.junit.*;
import org.junit.Test;
import ru.tstu.msword_automation.automation.constants.ReplacementStrategy;
import ru.tstu.msword_automation.automation.constants.SaveFormat;
import ru.tstu.msword_automation.automation.constants.SaveOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static org.junit.Assert.*;


public class BasicDocumentTest {
	private static final String DOC_NAME = "testing_doc.docx";
	private static final String DOC_CONTENT = "asdf";
	private static final int DOC_CONTENT_COUNT = 4;
	private static String testingDocPath;
	private static String resourcesRoot;
	private TestableBasicDocument doc;


	@BeforeClass
	public static void setUpOnce() {
		ClassLoader classLoader = BasicDocumentTest.class.getClassLoader();
		String jacobDllPath = classLoader.getResource("jacob-1.14.3-x64.dll").getPath();
		jacobDllPath = jacobDllPath.substring(1);
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, jacobDllPath);
		testingDocPath = classLoader.getResource(DOC_NAME).getPath();
		resourcesRoot = classLoader.getResource(".").getPath();

		// that needed to remove "/" at the beginning of the path
		testingDocPath = testingDocPath.substring(1);
		resourcesRoot = resourcesRoot.substring(1);
	}

	@Before
	public void setUp() {
		doc = new TestableBasicDocument(testingDocPath);
	}

	@After
	public void closeDoc() {
		doc.close(SaveOptions.DO_NOT_SAVE);
	}

	@AfterClass
	public static void tearDownOnce() {
		WordApplication.close();
	}


	@Test
	public void CorrectNameFromDefinedInstanceOfDocument() {
		assertEquals(DOC_NAME, doc.getName());
	}

	@Test
	public void whenFindInLoopThenCorrectNumberOfFound() {
		int count = findingCount(DOC_CONTENT);
		assertEquals(DOC_CONTENT_COUNT, count);
	}

	@Test
	public void ReplacingAllOccurrences() {
		String replacement = "fdsa";
		doc.replace(DOC_CONTENT, replacement, ReplacementStrategy.REPLACE_ALL);
		int count = findingCount(replacement);
		assertEquals(DOC_CONTENT_COUNT, count);
	}

	@Test
	public void ReplacingAllOccurrencesUsingShorthandMethod() {
		String replacement = "fdsa";
		doc.replace(DOC_CONTENT, replacement);
		int count = findingCount(replacement);
		assertEquals(DOC_CONTENT_COUNT, count);
	}

	@Test
	public void whenReplaceAndSaveThenChangesActuallySaved() throws Exception {
		final String copyPath = resourcesRoot + "/" + "testing_doc_with_replacement.docx";
		this.makeDocumentCopy(copyPath);

		Document copiedDoc = new TestableBasicDocument(copyPath);
		String replacement = "fdsa";
		copiedDoc.replace("asdf", replacement);
		copiedDoc.close(SaveOptions.SAVE);

		Document openedAgain = new TestableBasicDocument(copyPath);
		int count = findingCount(replacement, openedAgain);
		openedAgain.close();
		assertEquals(DOC_CONTENT_COUNT, count);
	}

	@Test
	public void CorrectPathWhenDocWasSavedAsToAnotherFolder() throws Exception {
		Path saveAsFolder = Paths.get(resourcesRoot + "/" + "test_saved_as");
		removeDirectoryRecursively(saveAsFolder);
		Files.createDirectory(saveAsFolder);

		String fileName = "saved_as.docx";
		doc.saveAs(saveAsFolder.toString(), fileName, SaveFormat.DOCX);
		String fullPath = saveAsFolder + "\\" + fileName;
		assertEquals(fullPath, doc.getFullPath());
	}

	@Test
	public void whenClosedWithoutSavingThenChangesHaveNotSaved() throws Exception {
		String replacement = "dsfa";
		doc.replace(DOC_CONTENT, replacement);
		doc.close();
		doc = new TestableBasicDocument(testingDocPath);
		int expected = findingCount(DOC_CONTENT);
		assertEquals(expected, DOC_CONTENT_COUNT);
	}


	private int findingCount(String key) {
		return findingCount(key, this.doc);
	}

	private int findingCount(String key, Document document) {
		int count = 0;
		while (document.find(key)){
			++count;
		}

		return count;
	}

	// makes copy of testable document in resources root. replases if it's already exist.
	private void makeDocumentCopy(String copyPath) throws Exception {
		Path originalFile = new File(testingDocPath).toPath();
		Path copyLocation = new File(copyPath).toPath();
		if(copyLocation.toFile().exists()) {
			Files.delete(copyLocation);
		}
		Files.copy(originalFile, copyLocation);
	}

	private void removeDirectoryRecursively(Path folder) throws Exception {
		if(Files.exists(folder)) {
			Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}
			});

		}

	}



	// TODO joint compilation in testing scope




}