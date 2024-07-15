package com.spring.shoestore.offline.service;

import com.spring.shoestore.offline.dto.ProductDto;
import com.spring.shoestore.offline.repository.ProductDtoRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class ProductDtoService {
    @Autowired
    private ProductDtoRepository productDtoRepository;

    public Page<ProductDto> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return productDtoRepository.findAllQuantity(pageable);
    }

    public void exportFile(List<ProductDto> productDtoList, String filePath, String fileName) {
        try {
            File file = new File(filePath, fileName + ".xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Products");

            String[] headers = {"STT", "ProductCode", "ProductName", "Category", "Sole", "Brand", "Material", "Quantity"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowNum = 0;
            for (ProductDto productDto : productDtoList) {
                Row row = sheet.createRow(++rowNum);
                row.createCell(0).setCellValue(rowNum);
                row.createCell(1).setCellValue(productDto.getProduct_code());
                row.createCell(2).setCellValue(productDto.getProduct_name());
                row.createCell(3).setCellValue(productDto.getCategory_name());
                row.createCell(4).setCellValue(productDto.getSole_name());
                row.createCell(5).setCellValue(productDto.getBrand_name());
                row.createCell(6).setCellValue(productDto.getMaterial_name());
                row.createCell(7).setCellValue(productDto.getQuantity());
            }

            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
                System.out.println("Excel file created successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
