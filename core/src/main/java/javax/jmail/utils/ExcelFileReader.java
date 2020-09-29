package javax.jmail.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * The Class ExcelFileReader.
 */
public class ExcelFileReader {

	/** The receiver. */
	private final String[] receiver = { "receiver", "to", "acceptor", "collector", "target", "recipient" };

	/** The sub. */
	private final String[] sub = { "sub", "subject", "topic", "proposal", "principal", "title", "objective", "header",
			"head", "purpose" };

	/** The msg. */
	private final String[] msg = { "msg", "message", "body", "content" };

	/** The ind. */
	/* ind hold index of receiver, subject, and content. */
	private final int[] ind = new int[3];

	/** The detail. */
	final ArrayList<ArrayList<String>> detail = new ArrayList<>();

	/** The s. */
	private boolean m = false, r = false, s = false;

	/** The file. */
	private final File file;

	/**
	 * Instantiates a new excel file reader.
	 *
	 * @param file the file
	 */
	public ExcelFileReader(File file) {
		this.file = file;
	}

	/**
	 * Init the.
	 *
	 * @param index the index
	 * @param data  the data
	 */

	private void init(int index, String data) {
		for (int i = 0; !m && i < msg.length; i++) {
			if (data.equalsIgnoreCase(msg[i])) {
				m = true;
				ind[2] = index;
				break;
			}
		}
		for (int i = 0; !r && i < receiver.length; i++) {
			if (data.equalsIgnoreCase(receiver[i])) {
				r = true;
				ind[0] = index;
				break;
			}
		}
		for (int i = 0; !s && i < sub.length; i++) {
			if (data.equalsIgnoreCase(sub[i])) {
				s = true;
				ind[1] = index;
				break;
			}
		}
		m = false;
		r = false;
		s = false;
	}

	/**
	 * Init the detail.
	 *
	 * @param row the row
	 */
	private void initDetail(XSSFRow row) {
		ArrayList<String> data = new ArrayList<>();

		String receiver = row.getCell(ind[0]).getStringCellValue();
		String subject = row.getCell(ind[1]).getStringCellValue();
		String content = row.getCell(ind[2]).getStringCellValue();

		data.add(receiver);
		data.add(subject);
		data.add(content);

		detail.add(data);
	}

	/**
	 * Read as individual. Receiver, Subject, message
	 *
	 * @return the array list
	 * @throws Exception the exception
	 */
	public ArrayList<ArrayList<String>> readAsIndividual() throws Exception {

		try (XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file))) {
			XSSFSheet sheet = wb.getSheetAt(0);
			int i = sheet.getFirstRowNum();

			sheet.getRow(i++).forEach(e -> init(e.getColumnIndex(), e.getStringCellValue()));

			while (i < sheet.getLastRowNum() + 1)
				initDetail(sheet.getRow(i++));
		}
		return detail;
	}

	/**
	 * Read as group.
	 *
	 * @return the array list
	 * @throws Exception the exception
	 */
	public ArrayList<String> readAsGroup() throws Exception {

		ArrayList<String> detail = new ArrayList<>();

		try (XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file))) {
			XSSFSheet sheet = wb.getSheetAt(0);
			int i = sheet.getFirstRowNum();

			sheet.getRow(i++).forEach(e -> init(e.getColumnIndex(), e.getStringCellValue()));

			while (i < sheet.getLastRowNum() + 1)
				detail.add(sheet.getRow(i++).getCell(ind[0]).getStringCellValue());
		}
		return detail;
	}

}
