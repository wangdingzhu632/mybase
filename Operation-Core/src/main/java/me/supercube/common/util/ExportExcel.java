package me.supercube.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;


/**
 *
 * 导出指定的对话到Excel
 *
 * <pre>
 *
 *      ExportExcel<UcAlarmlog> ex = new ExportExcel<UcAlarmlog>();
 *       String[] headers = { "学号", "姓名", "年龄", "性别", "出生日期" };
 *       List<Student> dataset = new ArrayList<Student>();
 *       dataset.add(new Student(10000001, "张三", 20, true, new LocalDateTime()));
 *       dataset.add(new Student(20000002, "李四", 24, false, new LocalDateTime()));
 *       dataset.add(new Student(30000003, "王五", 22, true, new LocalDateTime()));
 *        List<Book> dataset2 = new ArrayList<Book>();
 *       try {
 *           OutputStream out = new FileOutputStream("E://a.xls");
 *           ex.exportExcel(headers, dataset, out);
 *           out.close();
 *           System.out.println("excel导出成功！");
 *       } catch (FileNotFoundException e) {
 *           e.printStackTrace();
 *       } catch (IOException e) {
 *           e.printStackTrace();
 *       }
 *
 * </pre>
 *
 *
 * @author chenping
 * */
public class ExportExcel<T> {


	public static void main(String[] args) {

        /*// 测试
        ExportExcel<UcAlarmlog> ex = new ExportExcel<UcAlarmlog>();
        String[] headers = { "学号", "姓名", "年龄", "性别", "出生日期" };
        List<Student> dataset = new ArrayList<Student>();
        dataset.add(new Student(10000001, "张三", 20, true, new LocalDateTime()));
        dataset.add(new Student(20000002, "李四", 24, false, new LocalDateTime()));
        dataset.add(new Student(30000003, "王五", 22, true, new LocalDateTime()));
        List<Book> dataset2 = new ArrayList<Book>();
        try {
            OutputStream out = new FileOutputStream("E://a.xls");
            ex.exportExcel(headers, dataset, out);
            out.close();
            System.out.println("excel导出成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 *
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格标题列名数组
	 * @param fields
	 *            表格属性列名数组,必须与标题一一对应
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,LocalDateTime,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd HH:mm:ss"
	 *
	 * @author chenping
	 */
	public void exportExcel(String title, String[] headers, String[] fields,
			Collection<T> dataset, OutputStream out, String pattern) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		exportExcel(workbook,title, headers, fields,dataset, out, pattern);
	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 *
	 * @param workbook 工作薄对象,用于创建多个Sheet页时调用，声明如下：<pre>HSSFWorkbook workbook = new HSSFWorkbook();</pre>
	 *
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格标题列名数组
	 * @param fields
	 *            表格属性列名数组,必须与标题一一对应
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,LocalDateTime,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd HH:mm:ss"
	 *
	 * @author chenping
	 */
	public void exportExcel(HSSFWorkbook workbook,String title, String[] headers, String[] fields,
			Collection<T> dataset, OutputStream out, String pattern) {
		// 声明一个工作薄
		//HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setItalic(false);
		font.setFontHeightInPoints((short) 10);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 8);
		font2.setColor(HSSFColor.BLACK.index);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		// HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
		// 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		// comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		// comment.setAuthor("");

		// 产生表格中文标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		if (pattern == null || pattern.equals("")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			// Field[] fields = t.getClass().getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				String fieldName = fields[i];
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);

				// 值
				Object value = null;
				Class cls = t.getClass();
				Method getMethod = null;

				try {
					getMethod = cls.getMethod(getMethodName);
				} catch (NoSuchMethodException e1) {
					try {
						//获取字段，判断字段类型，如果为布尔型的话，尝试用is方法
						Field field = cls.getDeclaredField(fieldName);
						if(field.getGenericType().equals(Boolean.TYPE)) {
							getMethodName = "is"
									+ fieldName.substring(0, 1).toUpperCase()
									+ fieldName.substring(1);
							getMethod = cls.getMethod(getMethodName
                            );
						}
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					}

				} catch (SecurityException e1) {
					e1.printStackTrace();
				}

				try {

					value = getMethod.invoke(t);

					// 判断值的类型后进行强制类型转换
					String textValue = null;

					if (value instanceof Boolean) {
						boolean bValue = (Boolean) value;
						textValue = "true";
						if (!bValue) {
							textValue = "false";
						}
					} else if (value instanceof LocalDateTime) {
						LocalDateTime date = (LocalDateTime) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
								1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(
								bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {
						if (value != null) {
							textValue = value.toString();
						} else {
							textValue = "";
						}
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							cell.setCellValue(textValue);
						}
					}
				} catch (SecurityException e) {

					e.printStackTrace();
				} catch (IllegalArgumentException e) {

					e.printStackTrace();
				} catch (IllegalAccessException e) {

					e.printStackTrace();
				} catch (InvocationTargetException e) {

					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}

		}

		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



}
