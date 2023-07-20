package com.project.bookstore.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.project.bookstore.Entity.Category;
import com.project.bookstore.Repository.CategoryRepo;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperReportService {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryRepo repo;
	
	public byte[] exportCategoryReport() throws FileNotFoundException, JRException {
		
		List<Category> allCategory = categoryService.getAllCategory(0, 50, "");
		
		
		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
		
		//load file
		
		File file = ResourceUtils.getFile("classpath:CategoryReportTemplate.jrxml");
		
		JasperReport compileReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		
		JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(allCategory);
		
		Map<String, Object> parameter=new HashMap<>();
		parameter.put("Created By:", "Mohammed Ruman");
		
		JasperPrint fillReport = JasperFillManager.fillReport(compileReport,parameter, dataSource );
		
		
		
		JasperExportManager.exportReportToPdfStream(fillReport, byteArrayOutputStream);
		
		JasperExportManager.exportReportToPdfFile(fillReport,"/home/user/Downloads/flat-design-library-logo-template/report.pdf");
		
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		return byteArray;
		
	}
}
