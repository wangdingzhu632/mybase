package me.supercube.common.util;

import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.tools.zip.ZipEntry;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 文件工具类,包括文件目录创建、上传、下载、删除等常用操作
 *
 * @author CHENPING
 * @version 1.0
 */
public class FileUtil {

	private static final Log logger = LogFactory.getLog(FileUtil.class);

	public final static long ONE_KB = 1024;
	public final static long ONE_MB = ONE_KB * 1024;
	public final static long ONE_GB = ONE_MB * 1024;
	public final static long ONE_TB = ONE_GB * (long) 1024;
	public final static long ONE_PB = ONE_TB * (long) 1024;

	static int COUNT = 2;

	/**
	 * 上传文件到服务器 <br />
	 * 注意： input名称必须为file
	 *
	 * @param request
	 *            HTTP请求对象
	 * @param newFileName
	 *            重命名文件名
	 * @param dirName
	 *            目标目录名称
	 *
	 * @return 文件路径
	 */
	public static String uploadFile(HttpServletRequest request, String newFileName, String dirName) throws Exception {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile mpFile = multipartRequest.getFile("file");// 获取文件
		String fileName = mpFile.getOriginalFilename();// 获取文件名称
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);// 获取后缀
		if (!"".equals(newFileName)) {
			fileName = newFileName + "." + suffix;// 自定义名称
		}
		String path = request.getSession().getServletContext().getRealPath("/") + dirName;// 设置文件保存路径
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String ret = null;
		try {
			SaveFileFromInputStream(mpFile.getInputStream(), path, fileName);

			if (!dirName.endsWith("\\")) {
				dirName += "/";
			}
			// 文件路径
			ret = dirName + fileName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 保存文件
	 *
	 * @param stream
	 *            输入流
	 * @param path
	 *            保存路径
	 * @param filename
	 *            文件名称
	 * @throws IOException
	 */
	public static void SaveFileFromInputStream(InputStream stream, String path, String filename) throws IOException {
		File targetFile = new File(path);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		String realPath = path + "/" + filename;
		FileOutputStream fs = new FileOutputStream(realPath);
		byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}

	/**
	 * 删除指定的文件
	 *
	 * @param filePath
	 *            文件地址
	 *
	 * @return if success is true,else false
	 */
	public static boolean deleteFile(String filePath) {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			return false;
		}
		return targetFile.delete();
	}

	/**
	 * 返回指定的文件是否存在
	 *
	 * @param filePath
	 *            文件或目录
	 *
	 * @return
	 */
	public static boolean exist(String filePath) {
		File targetFile = new File(filePath);
		return targetFile.exists();
	}

	/**
	 * 返回指定的路径是否是文件
	 *
	 * @param filePath
	 */
	public static boolean isFile(String filePath) {
		File targetFile = new File(filePath);
		return targetFile.isFile();
	}

	public static File[] getFiles(HttpServletRequest request, String path) {
		String realPath = request.getSession().getServletContext().getRealPath("/") + "/" + path;
		File targetFile = new File(realPath);
		File[] files = targetFile.listFiles();
		return files;
	}

	public static void copy(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			logger.error("FileUtil copyFile Error", e);
			e.printStackTrace();
		}
	}

	public static void copy(File oldfile, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldfile);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("error  ");
			e.printStackTrace();
		}
	}

	public static boolean move(File srcFile, String destPath) {
		// Destination directory
		File dir = new File(destPath);

		// Move file to new directory
		boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));

		return success;
	}

	public static boolean move(String srcFile, String destPath) {
		// File (or directory) to be moved
		File file = new File(srcFile);

		// Destination directory
		File dir = new File(destPath);

		// Move file to new directory
		boolean success = file.renameTo(new File(dir, file.getName()));

		return success;
	}

	/**
	 * 保存小牛配置信息
	 *
	 * @param obj
	 * @param filename
	 * @param request
	 */
	public static void saveconfigToFile(Object obj, String filename, HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/") + "/"
				+ "resources/structures/Netvideo/Config";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		FileOutputStream fos = null;
		Writer wo = null;
		File f = new File(file + "/" + filename + ".ini");
		if (f.exists()) {
			f.delete();
		}
		JSONArray jsonArray = JSONArray.fromObject(obj);
		try {
			fos = new FileOutputStream(file + "/" + filename + ".ini", true);
			wo = new OutputStreamWriter(fos);
			wo.write(jsonArray.toString());
			wo.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wo != null)
					wo.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 读取dat文件
	 *
	 * @param filename
	 * @param request
	 * @return
	 */
	public static String readConfig(String filename, HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/") + "/"
				+ "resources/structures/Netvideo/Config/" + filename + ".ini";
		File f = new File(path);
		String str = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			String line = null;
			while ((line = br.readLine()) != null) {
				str = str + line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return str;
	}

	/**
	 * 驱动下载
	 *
	 * @param request
	 * @param response
	 * @param drivername
	 * @throws IOException
	 */
	public static void downLoadDriver(HttpServletRequest request, HttpServletResponse response, String drivername) {
		String filePath = request.getSession().getServletContext().getRealPath("/") + "/"
				+ "resources/structures/Netvideo/Driver/" + drivername;
		File f = new File(filePath);
		OutputStream os = null;
		BufferedInputStream bis = null;
		try {
			if (!f.exists()) {
				response.sendError(404, "file not fond");
			}
			bis = new BufferedInputStream(new FileInputStream(f));
			byte[] buf = new byte[1024];
			int len = 0;

			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment;filename=" + f.getName());
			os = response.getOutputStream();
			while ((len = bis.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
				if (bis != null)
					bis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String getCellValue(Cell cell) {
		if (cell != null) {
			// setMethod.invoke()将实际调用之前Java反射机制所获取到的Set方法，其中第一个参数是被Set赋值的对象（变量或元素），第二个参数是Set进去的值
			// 由于之前使用getMethod()方法获取的方法都符合“带有一个String类型参数”的规范，因此这里第二个参数也必须转换为String类型
			switch (cell.getCellType()) // 判断单元格中数据的类型
			{
			case Cell.CELL_TYPE_BLANK: // 空
			case Cell.CELL_TYPE_ERROR: // 错误 ERROR
				return "";
			case Cell.CELL_TYPE_BOOLEAN: // Boolean
				return cell.getBooleanCellValue() ? "1" : "0";
			case Cell.CELL_TYPE_FORMULA: // 公式
			case Cell.CELL_TYPE_NUMERIC: // 数值
				return String.valueOf((long) (cell.getNumericCellValue()));
			case Cell.CELL_TYPE_STRING: // 字符串
				return cell.getStringCellValue();
			}
		}
		return "";
	}

	/**
	 * 获取驱动目录下的文件信息
	 */
	public static List<String> getNetvideoFiles(String path, HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/") + path;
		List<String> fileStrings = new ArrayList<String>();
		fileStrings.add(1 + "*" + 0 + "*" + "Driver" + "*" + "dire" + "*" + realPath);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fileStrings", fileStrings);
		map = getfilesString(1, realPath, map);
		COUNT = 2;
		return (List<String>) map.get("fileStrings");
	}

	/**
	 * 获取文件列表，递归
	 *
	 * @param p_index
	 *            文件夹的id
	 * @param path
	 *            文件夹路径
	 * @param map
	 *            存放文件夹及文件信息
	 * @return
	 */
	static Map<String, Object> getfilesString(int p_index, String path, Map<String, Object> map) {
		File root = new File(path);
		File[] files = root.listFiles();
		List<String> fileStrings = (List<String>) map.get("fileStrings");
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String filepath = files[i].getAbsolutePath();
				int fileIndex = COUNT;
				String filename = files[i].getName();
				// 编号*父编号*+路径
				if (files[i].isDirectory()) {
					fileStrings.add(fileIndex + "*" + p_index + "*" + filename + "*" + "dire" + "*" + filepath);
				} else {
					fileStrings.add(fileIndex + "*" + p_index + "*" + filename + "*" + "file" + "*" + filepath);
				}
				COUNT++;
				map.put("fileStrings", fileStrings);
				if (files[i].isDirectory()) {
					map = getfilesString(fileIndex, files[i].getAbsolutePath(), map);
				}
			}
		}
		map.put("fileStrings", fileStrings);
		return map;
	}

	// 文件夹的增删改
	public static void processDir(String type, String name, String path) {
		if (type.equals("add")) {
			String dirPath = path + "\\" + name;
			File file = new File(dirPath);
			file.mkdir();
		} else if (type.equals("modify")) {
			int b_num = path.lastIndexOf('\\');
			String newPathString = path.substring(0, b_num);
			File file = new File(newPathString + "\\" + name);
			file.mkdir();
		} else {
			File file = new File(path);
			deleteDirAndFile(file);
		}
	}

	// 删除文件夹及其下的文件
	static void deleteDirAndFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isFile()) {
					file2.delete();
				} else {
					deleteDirAndFile(file2);
				}
			}
			file.delete();
		} else {
			file.delete();
		}
	}

	/**
	 *
	 * 得到文件大小。
	 *
	 * @param fileSize
	 * @return
	 */
	public static String getHumanReadableFileSize(long fileSize) {
		if (fileSize < 0) {
			return String.valueOf(fileSize);
		}
		String result = getHumanReadableFileSize(fileSize, ONE_PB, "PB");
		if (result != null) {
			return result;
		}

		result = getHumanReadableFileSize(fileSize, ONE_TB, "TB");
		if (result != null) {
			return result;
		}
		result = getHumanReadableFileSize(fileSize, ONE_GB, "GB");
		if (result != null) {
			return result;
		}
		result = getHumanReadableFileSize(fileSize, ONE_MB, "MB");
		if (result != null) {
			return result;
		}
		result = getHumanReadableFileSize(fileSize, ONE_KB, "KB");
		if (result != null) {
			return result;
		}
		return String.valueOf(fileSize) + "B";
	}

	private static String getHumanReadableFileSize(long fileSize, long unit, String unitName) {
		if (fileSize == 0)
			return "0";

		if (fileSize / unit >= 1) {
			double value = fileSize / (double) unit;
			DecimalFormat df = new DecimalFormat("######.##" + unitName);
			return df.format(value);
		}
		return null;
	}

	public static String getFileExt(String fileName) {
		if (fileName == null)
			return "";

		String ext = "";
		int lastIndex = fileName.lastIndexOf(".");
		if (lastIndex >= 0) {
			ext = fileName.substring(lastIndex + 1).toLowerCase();
		}

		return ext;
	}

	/**
	 * 得到不包含后缀的文件名字。
	 *
	 * @return
	 */
	public static String getRealName(String name) {
		if (name == null) {
			return "";
		}

		int endIndex = name.lastIndexOf(".");
		if (endIndex == -1) {
			return null;
		}
		return name.substring(0, endIndex);
	}


	/**
     * @desc 将源文件/文件夹生成指定格式的压缩文件,格式zip
     * @param resourcesPath 源文件/文件夹
     * @param targetPath  目的压缩文件保存路径
     * @return void
     * @throws Exception
     */
    public static void compressedFile(String resourcesPath,String targetPath) throws Exception{
        File resourcesFile = new File(resourcesPath);     //源文件
        File targetFile = new File(targetPath);           //目的
        //如果目的路径不存在，则新建
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        //源文件和目标文件保存路径不能一样，会导致重复循环
    	if(resourcesFile.isDirectory() && resourcesPath.equals(targetPath)) {
    		throw new Exception("源文件和目标文件保存路径不能一样");
    	}

        String targetName = resourcesFile.getName()+".zip";   //目的压缩文件名
        FileOutputStream outputStream = new FileOutputStream(targetPath+File.separator+targetName);
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(outputStream));

        createCompressedFile(out, resourcesFile, "");

        out.close();
    }

    /**
     * @desc 生成压缩文件。
     *                  如果是文件夹，则使用递归，进行文件遍历、压缩
     *       如果是文件，直接压缩
     * @param out  输出流
     * @param file  目标文件
     * @return void
     * @throws Exception
     */
    public static void createCompressedFile(ZipOutputStream out,File file,String dir) throws Exception{
        //如果当前的是文件夹，则进行进一步处理
        if(file.isDirectory()){
            //得到文件列表信息
            File[] files = file.listFiles();
            //将文件夹添加到下一级打包目录
            out.putNextEntry(new ZipEntry(dir+File.separator));

            dir = dir.length() == 0 ? "" : dir + File.separator;

            //循环将文件夹中的文件打包
            for(int i = 0 ; i < files.length ; i++){
                createCompressedFile(out, files[i], dir + files[i].getName());         //递归处理
            }
        }
        else{   //当前的是文件，打包处理
            //文件输入流
            FileInputStream fis = new FileInputStream(file);

            out.putNextEntry(new ZipEntry(dir));
            //进行写操作
            int j =  0;
            byte[] buffer = new byte[1024];
            while((j = fis.read(buffer)) > 0){
                out.write(buffer,0,j);
            }
            //关闭输入流
            fis.close();
        }
    }
}
